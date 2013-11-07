/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.datastorehandlers;

import netra.helpers.SocialDatum;

/**
 *
 * @author manthanhd
 */
public abstract class TwitterDBHandler {
    
    abstract public void insert(SocialDatum datum, String tablename);
    abstract public void insert(SocialDatum[] datums, String tablename);
    abstract public SocialDatum[] retrieveNew(String tablename);
    abstract public SocialDatum[] retrieveWithKeyword(String condition, String tablename);
    abstract public SocialDatum[] retrieveAll(String tablename);
    
}
