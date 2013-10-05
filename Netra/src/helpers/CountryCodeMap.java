/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author manthanhd
 */
public class CountryCodeMap extends DefaultHandler {

    public HashMap<String, Country> map;
    private String code, name;
    private int population;
    private double area;
    
    private boolean in_country = false;
    private boolean in_cc;
    private boolean in_pop;
    private boolean in_area;
    private boolean in_parentCountry;
    
    public void init() {
        String filename = "countryInfo.xml";
        try {
//            File file = new File(filename);
//            if(file.exists()){
//                System.out.println("File " + filename + " exists!");
//            }
            
//            String fileContent = readFile(filename);s
//            CountryCodeMap ccm = new CountryCodeMap();
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader reader = saxParser.getXMLReader();
            reader.setContentHandler(this);
            reader.parse(convertToFileURL(filename));
//            return ccm;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CountryCodeMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CountryCodeMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CountryCodeMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CountryCodeMap.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return null;
    }
    
    private String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }
    
    public String readFile(String filename) throws FileNotFoundException, IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        while((line = reader.readLine()) != null){
            builder.append(line);
        }
        reader.close();
        System.out.println("Read: " + builder.toString().substring(0, 50));
        return builder.toString();
    }

    @Override
    public void startDocument() throws SAXException {
        map = new HashMap<>();
    }
    
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println("At start Element");
        if(qName.equals("country")) {
            in_parentCountry = true;
        } else if(qName.equals("countryCode")){
            in_cc =  true;
        } else if(qName.equals("countryName")){
            in_country = true;
        } else if(qName.equals("population")){
            in_pop = true;
        } else if(qName.equals("areaInSqKm")){
            in_area = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(in_cc){
            code = new String(ch,start,length);
        } else if(in_country){
            name = new String(ch,start,length);
        } else if(in_pop){
            population = Integer.parseInt(new String(ch,start,length));
        } else if(in_area){
            area = Double.parseDouble(new String(ch,start,length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("country")) {
            in_parentCountry = false;
            map.put(code, new Country(name, population, area));
        } else if(qName.equals("countryCode")){
            in_cc =  false;
        } else if(qName.equals("countryName")){
            in_country = false;
        } else if(qName.equals("population")){
            in_pop = false;
        } else if(qName.equals("areaInSqKm")){
            in_area = false;
        }
    }
    
    
    
    
}
