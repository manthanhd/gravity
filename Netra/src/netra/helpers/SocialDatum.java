/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.helpers;

import com.google.gson.Gson;
import java.util.Date;
import java.util.HashMap;
import twitter4j.Status;

/**
 *
 * @author manthanhd
 */
public class SocialDatum implements Datum {

    /*
     * 1    :   username
     * 2    :   data
     * 3    :   importance
     * 4    :   retweet_count
     * 5    :   is_retweet
     * 6    :   date_posted
     * 7    :   country_name
     */
    
    String data;//, userName;
    //long retweetCount;
    double importance;
//    double lat, lon;
//    boolean geoLocationAvailable = false;
    
    //private boolean isRetweet = false;
    private Date datePosted;
    //private String countryName = null;
    
    HashMap<String, Object> properties = new HashMap<>();

    //    public boolean isGeoLocationAvailable(){
    //        return geoLocationAvailable;
    //    }
    //    public double getLatitude(){
    //        return lat;
    //    }
    //
    //    public double getLongitude(){
    //        return lon;
    //    }
    
    public SocialDatum(String data, String userName, String countryName, boolean isRetweet, long retweetCount, double importance, Date datePosted) {
        this.data = data;
        this.importance = importance;
        this.datePosted = datePosted;
        properties.put(Properties.USERNAME, userName);
        properties.put(Properties.COUNTRY_NAME, countryName);
        properties.put(Properties.IS_RETWEET, isRetweet);
        properties.put(Properties.RETWEET_COUNT, retweetCount);
    }
    
    public SocialDatum(Status tweet){
        data = tweet.getText();
        properties.put(Properties.USERNAME, tweet.getUser().getName());
        importance = calculateImportance(tweet);
        
        if(tweet.getPlace()!=null){
            properties.put(Properties.COUNTRY_NAME, tweet.getPlace().getCountry());
        }
        properties.put(Properties.IS_RETWEET, tweet.isRetweet());
        properties.put(Properties.RETWEET_COUNT, tweet.getRetweetCount());
        this.datePosted = tweet.getCreatedAt();
    }
    
    public HashMap<String, Object> getPropertiesMap(){
        return properties;
    }
    
    public String getUsername(){
        return properties.get(Properties.USERNAME) + "";
    }
    
    public long getRetweetCount(){
        return (long) properties.get(Properties.RETWEET_COUNT);
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
        return (boolean) properties.get(Properties.IS_RETWEET);
    }

    /**
     * @return the datePosted
     */
    public Date getDatePosted() {
        return datePosted;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return properties.get(Properties.COUNTRY_NAME) + "";
    }
    
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}