/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netra.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manthanhd
 */
public class PropertyManager {
    private String filename = null;
    private HashMap<String, String> map;
    private static PropertyManager instance;
    
    private PropertyManager(String filename) throws FileNotFoundException{
        this.filename = filename;
        map = new HashMap();
        initContent();
    }
    
    private void initContent() throws FileNotFoundException{
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
        String s = null;
        if(!map.isEmpty()){
            getMap().clear();
        }
        try {
            while((s = reader.readLine()) != null){
                String key = "", value = "";
                boolean isKey = true;
                char c;
                for(int i = 0;i<s.length();i++){
                    c = s.charAt(i);
                    if(c == '='){
                        isKey = false;
                    }
                    
                    if(isKey){
                        key += c;
                    } else {
                        value += c;
                    }
                }
                key = key.trim();
                value = value.trim();
                getMap().put(key, value);
            }
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void init(String filename) throws FileNotFoundException{
        instance = new PropertyManager(filename);
    }
    
    public static PropertyManager getInstance(){
        if(instance == null){
            throw new NullPointerException("Property Manager was not initialized!");
        }
        return instance;
    }

    /**
     * @return the map
     */
    public HashMap<String, String> getMap() {
        return map;
    }
}
