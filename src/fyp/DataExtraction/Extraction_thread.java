/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp.DataExtraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.RateLimitStatus;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author generals
 */
public class Extraction_thread implements Runnable{
   int appStartPoint,appSize;
   ArrayList<TwitterApplicationCredentials> list_app;
   volatile HashMap<Long,ArrayList<Long>> followers = new HashMap<>();
   int depth;
   String keyword;
    
   Extraction_thread(int appStartingPoint,int appSize, ArrayList<TwitterApplicationCredentials> list_app,int depth,String keyword)
   {
     this.appStartPoint=appStartingPoint;
     this.appSize=appSize;
     this.list_app=list_app;
     this.depth=depth;
     this.keyword=keyword;
   }
  
   private List<Long> getFollowers(Long userId)
   {
       long cursor=-1;
       IDs ids = null;
       Boolean repeatLookup = true;
       List<Long> l = new ArrayList<>(); 
       RateLimitStatus status = null;
      // String screenName="Gebran_Bassil";
       int k=0;
       Boolean getRateSleep = true;
       
       followers.put(userId,new ArrayList<Long>());
     
       for(int i=appStartPoint;i>=0;i=appStartPoint+k%appSize)
       {
           if(repeatLookup)
           {
                twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
               
                k++;
                //aussi prendre en consideration que le getRateLimitStatus a aussi une limite;????attention a ca;
                while(getRateSleep)
                {
                    try {
                           status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                           getRateSleep=false;
                           
                    } catch (TwitterException ex) {
                          // Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            // thread to sleep for 1000 milliseconds
                            System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes");                            
                            Thread.sleep(900000);
                            
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
                
                getRateSleep = true;
                
                if(status.getRemaining()>1)
                {
                    try {
                    ids = twitter.getFollowersIDs(userId, cursor);
                    }catch(TwitterException ex) {
                    //ca correspond a un account priver;
                    l.add(new Long(-1));
                    return  l; 
                  //  System.out.println("erreur a la ligne 91");
                 //   Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                }
                else
                {
                    continue;
                }
                
        
                while(status.getRemaining()>1){
                    if(cursor!=0)
                    {
                        cursor = ids.getNextCursor();
                        try {
                            ids=twitter.getFollowersIDs(userId, cursor);
                            //(ids.getNextCursor()
                        } catch (TwitterException ex) {
                            System.out.println("erreur a la ligne 112");
                            Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                    }
                    else
                    {
                        repeatLookup=false;
                        break;
                    }
                   try {
                       status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                   } catch (TwitterException ex) {
                        //    Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                             // thread to sleep for 1000 milliseconds
                             System.out.println("thread of "+this.keyword+ " will sleep for 15 minutes");                            
                             Thread.sleep(900000);
                             System.out.println("woke up");

                         }catch (Exception e) {
                             System.out.println(e);
                         }
                   }
                }
            }
           else 
           {
               break;
           }
       }
       followers.put(userId, (ArrayList<Long>) l);
       
       return l;
   }
   private boolean keyExist(Long key)
   {
       //return true if we had already brought followers of that id; so if it is already in the hashMap or if it is in cassandra;
       //i will do it right now on the hashMAP
       return followers.containsKey(key);
   }
   private void storeFollowers(Long idAccount,List<Long> followers)
   {
       //i will save them into cassandra; and into the hashMap;
       //right now i will do it only in the HashMap;
       this.followers.put(idAccount, (ArrayList<Long>) followers);
   }
   public void runOLD()
   {
       List<List<Long>> aTraiter = new ArrayList<>();
       Long keywordId = null;//execute lookup api to get the id of that keyword;
       //find id of the keyword;
       twitter4j.Twitter twitter = list_app.get(0).getTwitterConf();
       try {
        
         keywordId = twitter.lookupUsers(keyword).get(0).getId();
          
       } catch (TwitterException ex) {
           System.out.println("erreur a la ligne 168");
           Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
       }
       //found
       
       List<Long> key = new ArrayList<>();
       key.add(0,keywordId);
       aTraiter.add(aTraiter.size(),key);
       
       while(!aTraiter.isEmpty())
       {
           List<Long> task = aTraiter.remove(0);

           for(int i=0;i<task.size();++i)
           {
               if(!keyExist(task.get(i)))
               {
                   //get its followers;
                    List<Long> newFollowers = getFollowers(task.get(i));      
                    
                    if(newFollowers.size()==1 && newFollowers.get(0)== -1)
                    {
                        continue;
                    }
                    else
                    {
                        storeFollowers(task.get(i),newFollowers);
                        aTraiter.add(aTraiter.size(),newFollowers);
                    }
               }
           } 
       }  
   }
   
   public void run()
   {
   
       List<List<Long>> aTraiter = new ArrayList<>();
       Long keywordId = null;//execute lookup api to get the id of that keyword;
       //find id of the keyword;
       twitter4j.Twitter twitter = list_app.get(0).getTwitterConf();
       try {
        
         keywordId = twitter.lookupUsers(keyword).get(0).getId();
          
       } catch (TwitterException ex) {
           System.out.println("erreur a la ligne 217");
           Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
       }
       //found
      
       List<List<Long>> followedList = new ArrayList<>();
       List<Long> keywordList = new ArrayList<>();
       keywordList.add(keywordId);
       followedList.add(keywordList);
       
       List<List<Long>> tempFollowedList = new ArrayList<>();
       
       for(int i=0;i<depth;++i)
       {
           //getting followers
           for(int j=0;j<followedList.size();++j)
           {
               for(int k=0;k<followedList.get(j).size();++k)
               {
                   if(!keyExist(followedList.get(j).get(k)))
                   {
                        List<Long> newFollowers = getFollowers(followedList.get(j).get(k));
                        
                        if(newFollowers.size() == 1 && newFollowers.get(0)== -1)
                        {
                             continue;
                        }
                        else
                        {
                             tempFollowedList.add(newFollowers);
                             storeFollowers(followedList.get(j).get(k),newFollowers);  
                        }
                   }
               }
           }   
          followedList = new ArrayList<>();
          followedList.addAll(tempFollowedList);
          tempFollowedList = new ArrayList<>();
       }
   }
   public void runOld()
   {
       //chacun va chercher les followers ; et il les sauvegardra dans ca liste;
     //////une question : est ce que je rend ecriture sur la base de donner synchroniser/>? bi ra2ye la2; cause hiye elle va faire la gestion seul;
       
       long cursor=-1;

       IDs ids = null;
       Boolean repeatLookup = true;
       List<Long> l = new ArrayList<>(); 
       RateLimitStatus status = null;
       String screenName="Gebran_Bassil";
       int k=0;
       followers.put(new Long(1),new ArrayList<Long>());
     
       for(int i=appStartPoint;i>=0;i=appStartPoint+k%appSize)
       {
           if(repeatLookup)
           {

               twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
                k++;
                try {
                       status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                } catch (TwitterException ex) {
                       Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(status.getRemaining()>1)
                {
                    try {
                    ids = twitter.getFollowersIDs(screenName, cursor);
                    }catch(TwitterException ex) {
                    Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                }
                else
                {
                    continue;
                }
                
        
                while(status.getRemaining()>1){
                    if(cursor!=0)
                    {
                        cursor = ids.getNextCursor();
                        try {
                            ids=twitter.getFollowersIDs(screenName, cursor);
                            //(ids.getNextCursor()
                        } catch (TwitterException ex) {
                            Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
                    }
                    else
                    {
                        repeatLookup=false;
                        break;
                    }
                   try {
                       status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                   } catch (TwitterException ex) {
                       Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
            }
           else 
           {
               break;
           }
       }
       followers.put(new Long(1), (ArrayList<Long>) l);
       System.out.println("nombre de followers de x:"+ l.size());
       Long keywordId = null;
       twitter4j.Twitter twitter = list_app.get(0).getTwitterConf();
       try {
        
         keywordId = twitter.lookupUsers(keyword).get(0).getId();
          System.out.println(keyword+"  id is : "+keywordId);
          
       } catch (TwitterException ex) {
           Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
}
