/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp.DataExtraction;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author generals
 */
public class TwitterApplicationCredentials {
    String consumerKey;
    String consumerSecret;
    String token;
    String tokenSecret;
  
    public TwitterApplicationCredentials(String consumerKey,String consumerSecret,String token,String tokenSecret){
        this.consumerKey=consumerKey;
        this.consumerSecret=consumerSecret;
        this.token=token;
        this.tokenSecret=tokenSecret;
    }
    
    public twitter4j.Twitter getTwitterConf ()
    {
                ConfigurationBuilder cf=new ConfigurationBuilder();
                cf.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret); 
            
                TwitterFactory tf= new TwitterFactory(cf.build());
                twitter4j.Twitter twitter = tf.getInstance();
                return twitter;
    
    }

}
