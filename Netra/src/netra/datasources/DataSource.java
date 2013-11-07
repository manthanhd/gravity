/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.datasources;

import java.util.ArrayList;
import netra.helpers.SocialDatum;
import netra.helpers.StreamListener;

/**
 *
 * @author manthanhd
 */
public interface DataSource {
    
    public ArrayList<SocialDatum> searchDatums(String searchText);
    public ArrayList<SocialDatum> getCache();
    public void streamDatums(StreamListener listener);
    
}
