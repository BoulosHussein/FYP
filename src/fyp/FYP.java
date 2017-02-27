/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author generals
 */
public class FYP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TwitterException {
        String consumerKey="cYxtEVybhlxh2Oq9bo9hXpebA";
        String consumerSecret="va7Le5CL73sZzCmUGLN8ulaQkhBLvS3KEU9cy1kUeHqmzBhWwJ";
        String token="799227064624803841-bOBepZnuSnl6B8d82S4sTGTyWeWTk9E";
        String tokenSecret="p8taDFsbEkXIdHujJ01q3wDSADTgcv86WkXA19tw20P0U";
  
        ConfigurationBuilder cf=new ConfigurationBuilder();
        cf.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret);
        
        
        TwitterFactory tf= new TwitterFactory(cf.build());
        twitter4j.Twitter twitter = tf.getInstance();
        String screenName="Gebran_Bassil";
        long cursor=-1;
        IDs ids = null;
        PagableResponseList<User> list;
        //PagableResponseList users=null;
        System.out.println("Listing following ids.");
        int count= 0;
        do{
                RateLimitStatus status = twitter.getRateLimitStatus("followers").get("/followers/ids");
//                status.getRemaining();
                ids=twitter.getFollowersIDs(screenName, cursor);
                System.out.println("count="+count);
                count=count+ids.getIDs().length;
                
                //list=(PagableResponseList<User>) twitter.getFollowersList(screenName, cursor);
                
               /// for(User u :list){
               // System.out.println(u.getScreenName());
               // }
                for(long id : ids.getIDs()){
                System.out.println(id);
            }  
        }
        while((cursor=ids.getNextCursor())!=0);        
        System.exit(0);
    }
}
