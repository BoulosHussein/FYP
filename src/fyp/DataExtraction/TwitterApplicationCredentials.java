/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp.DataExtraction;

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

}
