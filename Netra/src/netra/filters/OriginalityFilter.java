/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.filters;

import netra.helpers.SocialDatum;
import java.util.ArrayList;

/**
 *
 * @author manthanhd
 */
public class OriginalityFilter {

    public ArrayList<SocialDatum> filter(ArrayList<SocialDatum> data) {
        ArrayList<SocialDatum> filteredData = new ArrayList<>();
        for(SocialDatum datum : data){
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
