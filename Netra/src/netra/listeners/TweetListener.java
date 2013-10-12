/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.listeners;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netra.datasources.TwitterDataSource;
import netra.datastorehandlers.MySQLTwitterDBHandler;
import netra.helpers.TwitterDatum;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 *
 * @author manthanhd
 */
public class TweetListener implements StatusListener{

    ArrayList<TwitterDatum> tweets;
    Logger logger;

    public TweetListener() {
        tweets = new ArrayList<>();
        logger = Logger.getLogger(TweetListener.class.getName());
    }
    
    @Override
    public void onStatus(Status status) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        logger.log(Level.INFO, "Status received.");
        TwitterDatum datum = new TwitterDatum(status);
        tweets.add(datum);
        logger.log(Level.INFO, "Now have " + tweets.size() + " tweets.");
        if(tweets.size() > 1000){
            logger.log(Level.INFO, "Buffer full. Dumping it in file.");
            TwitterDataSource.getInstance().dumpToFile("tweet_stream.txt", tweets);
            tweets.clear();
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // We won't delete statuses
        logger.log(Level.INFO, "Received a deletion notice. Request rejected.");
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        logger.log(Level.INFO, "Received track limitation notice.");
        TwitterDataSource.getInstance().dumpToFile("tweet_stream.txt", tweets);
        tweets.clear();
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        logger.log(Level.INFO, "Received notice to remove location. Request rejected.");
    }

    @Override
    public void onStallWarning(StallWarning warning) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        logger.log(Level.INFO, "Received stall warning.");
        TwitterDataSource.getInstance().dumpToFile("tweet_stream.txt", tweets);
        tweets.clear();
    }

    @Override
    public void onException(Exception ex) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        logger.log(Level.INFO, "Exception has occurred. Committing objects to database before I die.");
        TwitterDataSource.getInstance().dumpToFile("tweet_stream.txt", tweets);
        tweets.clear();
        logger.log(Level.INFO, "Exception:");
        ex.printStackTrace();
    }
    
}
