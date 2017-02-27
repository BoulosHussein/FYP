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
    
   Extraction_thread(int appStartingPoint,int appSize, ArrayList<TwitterApplicationCredentials> list_app)
   {
     this.appStartPoint=appStartingPoint;
     this.appSize=appSize;
     this.list_app=list_app;
   }
   
   public void run()
   {
       //chacun va chercher les followers ; et il les sauvegardra dans ca liste;
     //////une question : est ce que je rend ecriture sur la base de donner synchroniser/>? bi ra2ye la2; cause hiye elle va faire la gestion seul;
       long cursor=-1;
       String screenName="Gebran_Bassil";
       IDs ids = null;
       Boolean repeatLookup = true;
       List<Long> l = new ArrayList<>(); 
       //(cursor=ids.getNextCursor())!=0
       followers.put(new Long(1),new ArrayList<Long>());
       
       for(int i=appStartPoint;i<appStartPoint+appSize;++i)
       {
           if(repeatLookup)
           {
                ConfigurationBuilder cf=new ConfigurationBuilder();
                cf.setDebugEnabled(true).setOAuthConsumerKey(list_app.get(i).consumerKey)
                .setOAuthConsumerSecret(list_app.get(i).consumerSecret)
                .setOAuthAccessToken(list_app.get(i).token)
                .setOAuthAccessTokenSecret(list_app.get(i).tokenSecret); 
            
                TwitterFactory tf= new TwitterFactory(cf.build());
                twitter4j.Twitter twitter = tf.getInstance();
               
                try {
                ids = twitter.getFollowersIDs(screenName, cursor);
                }catch(TwitterException ex) {
                Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                l.addAll(Arrays.asList(ArrayUtils.toObject(ids.getIDs())));
          
                RateLimitStatus status = null;
        
                try {
                       status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                } catch (TwitterException ex) {
                       Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex);
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
   }
   
}
