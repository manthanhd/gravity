/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.filters;

import netra.helpers.TwitterDatum;
import java.util.ArrayList;

/**
 *
 * @author manthanhd
 */
public class OriginalityFilter {

    public ArrayList<TwitterDatum> filter(ArrayList<TwitterDatum> data) {
        ArrayList<TwitterDatum> filteredData = new ArrayList<>();
        for(TwitterDatum datum : data){
            if(!datum.isRetweet()){
                filteredData.add(datum);
            }
        }
        
        if(filteredData.isEmpty()){
            return null;
        }
        
        return filteredData;
    }
    
}
