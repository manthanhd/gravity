/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import twitter4j.Status;

/**
 *
 * @author manthanhd
 */
public class TwitterDatum implements Datum {

    String data, userName;
    long retweetCount;
    double importance;
    double lat, lon;
    boolean geoLocationAvailable = false;
    private boolean isRetweet = false;
    
    public boolean isGeoLocationAvailable(){
        return geoLocationAvailable;
    }
    
    public double getLatitude(){
        return lat;
    }
    
    public double getLongitude(){
        return lon;
    }
    
    public TwitterDatum(Status tweet){
        data = tweet.getText();
        userName = tweet.getUser().getName();
        importance = calculateImportance(tweet);
        geoLocationAvailable = (tweet.getGeoLocation() != null);
        if(geoLocationAvailable){
            lat = tweet.getGeoLocation().getLatitude();
            lon = tweet.getGeoLocation().getLongitude();
        }
        
        isRetweet = tweet.isRetweet();
        retweetCount = tweet.getRetweetCount();
    }
    
    @Override
    public String getData() {
        return data;
    }

    private double calculateImportance(Status tweet) {
        int followers = tweet.getUser().getFollowersCount();
        int following = tweet.getUser().getFriendsCount();
        
        long retweets = tweet.getRetweetCount();
        double result = 0;
        if(following > 0){
            result = followers / following;
        } else {
            result = followers;
        }
        if(retweets > 0 && !tweet.isRetweet()){
            result = result * retweets;
        }
        return result;
    }

    @Override
    public double getImportance() {
        return importance;
    }

    /**
     * @return the isRetweet
     */
    public boolean isRetweet() {
        return isRetweet;
    }
}