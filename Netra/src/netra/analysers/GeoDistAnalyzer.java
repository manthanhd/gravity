/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.analysers;

import netra.helpers.Country;
import netra.helpers.CountryCodeMap;
import netra.helpers.TwitterDatum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manthanhd
 */
public class GeoDistAnalyzer {
    
    private HashMap<String,ArrayList<TwitterDatum>> map;

    public GeoDistAnalyzer() {
        map = new HashMap<>();
    }
    
    public HashMap<String,ArrayList<TwitterDatum>> getAnalysis(){
        if(map.isEmpty()){
            return null;
        }
        return map;
    }
    
    public void analyze(ArrayList<TwitterDatum> statuses){
        System.out.println("GeoDistAnalyzer hard at work...");
        for(TwitterDatum tweet : statuses){
            if(tweet == null){
                continue;
            } else if (tweet.getCountryName() == null){
                // This means that geo location isn't available for that tweet.
                if(!map.containsKey("Unknown")){
                    map.put("Unknown", new ArrayList<TwitterDatum>());
                }
                
                map.get("Unknown").add(tweet);
                continue;
            }
            
//            double lat = tweet.getLatitude();
//            double lon = tweet.getLongitude();
//            
//            String address = "http://api.geonames.org/countryCode?lat=" + lat + "&lng=" + lon + "&username=manthanhd";
//            try {
//                String line = getUrlSource(address);
//                CountryCodeMap ccm = new CountryCodeMap();
//                ccm.init();    // This is a must
//                Country country = ccm.map.get(line);
//                boolean found = false;
//                for(String key : ccm.map.keySet()){
//                    if(key.equals(line)){
//                        found = true;
//                        String countryName = ccm.map.get(key).name;
//                        if(!map.containsKey(countryName)){
//                            map.put(countryName, new ArrayList<TwitterDatum>());
//                        }
//                        map.get(ccm.map.get(key).name).add(tweet);
//                    }
//                }
//                if(!found){
//                    map.put(line, new ArrayList<TwitterDatum>());
//                    map.get(line).add(tweet);
//                }
            if(map.containsKey(tweet.getCountryName())){
                map.get(tweet.getCountryName()).add(tweet);
            } else {
                map.put(tweet.getCountryName(), new ArrayList());
                map.get(tweet.getCountryName()).add(tweet);
            }
//            } catch (UnknownHostException ex) {
//                Logger.getLogger(GeoDistAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(GeoDistAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        System.out.println("GeoDistAnalyzer finished.");
    }
    
    private static String getUrlSource(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection uconn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                uconn.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        in.close();

        return stringBuilder.toString();
    }
}
