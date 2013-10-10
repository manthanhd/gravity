/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.helpers;

import com.google.gson.Gson;
import java.util.Date;
import twitter4j.Status;

/**
 *
 * @author manthanhd
 */
public class TwitterDatum implements Datum {

    /*
     * 1    :   username
     * 2    :   data
     * 3    :   importance
     * 4    :   retweet_count
     * 5    :   is_retweet
     * 6    :   date_posted
     * 7    :   country_name
     */
    
    String data, userName;
    long retweetCount;
    double importance;
//    double lat, lon;
//    boolean geoLocationAvailable = false;
    
    private boolean isRetweet = false;
    private Date datePosted;
    private String countryName = null;

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
    
    public TwitterDatum(String data, String userName, String countryName, boolean isRetweet, long retweetCount, double importance, Date datePosted) {
        this.data = data;
        this.userName = userName;
        this.countryName = countryName;
        this.isRetweet = isRetweet;
        this.retweetCount = retweetCount;
        this.importance = importance;
        this.datePosted = datePosted;
    }
    
    public TwitterDatum(Status tweet){
        data = tweet.getText();
        userName = tweet.getUser().getName();
        importance = calculateImportance(tweet);
//        geoLocationAvailable = (tweet.getGeoLocation() != null);
//        if(geoLocationAvailable){
//            lat = tweet.getGeoLocation().getLatitude();
//            lon = tweet.getGeoLocation().getLongitude();
//        }
        if(tweet.getPlace()!=null){
            this.countryName = tweet.getPlace().getCountry();
        }
        isRetweet = tweet.isRetweet();
        retweetCount = tweet.getRetweetCount();
        this.datePosted = tweet.getCreatedAt();
    }
    
    public String getUsername(){
        return userName;
    }
    
    public long getRetweetCount(){
        return retweetCount;
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
        return countryName;
    }
    
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}