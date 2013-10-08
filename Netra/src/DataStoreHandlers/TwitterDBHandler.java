/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStoreHandlers;

import helpers.TwitterDatum;

/**
 *
 * @author manthanhd
 */
public abstract class TwitterDBHandler {
    
    abstract public void insert(TwitterDatum datum, String tablename);
    abstract public void insert(TwitterDatum[] datums, String tablename);
    abstract public TwitterDatum[] retrieveNew(String tablename);
    abstract public TwitterDatum[] retrieveWithKeyword(String condition, String tablename);
    abstract public TwitterDatum[] retrieveAll(String tablename);
    
}
