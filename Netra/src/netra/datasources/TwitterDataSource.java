/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.datasources;

import com.google.gson.Gson;
import netra.helpers.TwitterDatum;
import netra.helpers.Tweet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.json.DataObjectFactory;

/**
 *
 * @author manthanhd
 */
public class TwitterDataSource implements DataSource{
    
    private static TwitterDataSource instance = null;
    private Twitter twitter;
    private TwitterStream stream;
    private ArrayList<Status> cache;
    
    private TwitterDataSource(){
        twitter = new TwitterFactory().getInstance();
        cache = new ArrayList<>();
        stream = new TwitterStreamFactory().getInstance();
    }
    
    public ArrayList<TwitterDatum> readFromFile(String filename) {
        ArrayList<TwitterDatum> statuses = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = null;
            Gson gson = new Gson();
            
            while((line = reader.readLine()) != null){
//                Gson gson = new Gson();
                TwitterDatum sd = gson.fromJson(line, TwitterDatum.class);
//                Status status = null;
//                try {
//                    status = DataObjectFactory.createStatus(line);
//                } catch (TwitterException ex) {
//                    Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
//                }
                statuses.add(sd);
            }
//            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!statuses.isEmpty()){
            return statuses;
        } else {
            return null;
        }
    }
    
    public void mergeFiles(String file1, String file2){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file1)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file2),true));
            String line = null;
            while((line = reader.readLine()) != null){
                writer.write(line);
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    public void dumpToFile(String filename, List<TwitterDatum> statuses){
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                Gson gson = new Gson();
                for(TwitterDatum sdatum : statuses){
                    writer.write(gson.toJson(sdatum) + System.lineSeparator());
                }
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(TwitterDataSource.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public ArrayList<Status> getCache(){
        return cache;
    }
    
    public ArrayList<TwitterDatum> searchDatums(String searchString){
        ArrayList<TwitterDatum> datums = new ArrayList<>();
        System.out.println("Acquiring...");
        ArrayList<Status> tweets = search(searchString);
        System.out.println("Converting to SocialDatums...");
        for(Status tweet : tweets){
            datums.add(new TwitterDatum(tweet));
        }
        System.out.println("Done.");
        return datums;
    }
    
    public ArrayList<Status> search(String searchString){
//                String searchString = JOptionPane.showInputDialog("Enter a search string...");
        twitter = new TwitterFactory().getInstance();
        ArrayList<Status> tweets = new ArrayList<>();
        try {
            Query query = new Query(searchString);
            
            query.count(100);
//            query.setLocale("en");
            query.setLang("en");
            QueryResult result;
            do {
                result = twitter.search(query);
//                System.out.println("Found " + result.getCount() + " results containing your search query.");
                tweets.addAll(result.getTweets());
//                for (Status tweet : tweets) {
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//                }
                
//                tweetCount += tweets.size();
            } while ((query = result.nextQuery()) != null);
            
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
//            System.exit(-1);
        } finally {
            if(!tweets.isEmpty()){
                cache.clear();
                cache.addAll(tweets);
                return tweets;
            } else {
                return null;
            }
        }
    }
    
    public void attachListener(StatusListener listener){
        stream.addListener(listener);
    }
    
    public TwitterStream getStream(){
        return stream;
    }
    
    public void startListening(){
        stream.sample();
    }
    
    public void stopStream(){
        stream.cleanUp();
        stream.shutdown();
    }
    
    public static TwitterDataSource getInstance(){
        if(instance == null){
            instance = new TwitterDataSource();
        }
        return instance;
    }
}
