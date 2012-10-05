/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jbserver.main.Main;
import org.jbserver.main.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jurgen
 */
public class StudioUpstairs implements Runnable {
    
    String data;
    String xmlurl;
    
    public void run() {
        this.parse();
        if(Main.isDebug()) {
            System.out.println("\"" + this.getData() + "\"");
            System.out.println("Run called!");
        }
    }
    
    public StudioUpstairs() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./config.cfg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.xmlurl = properties.getProperty("supstairsxmlurl");
    }

    public void parse() {
        try {
            String[] rawdata = Utilities.sendGet(this.xmlurl).split("\n");   
            if(rawdata[10] != null) {
                Pattern p = Pattern.compile("<B>(.*?)</B>");
                Matcher m = p.matcher(rawdata[10]);
                if(m.find()) {
                    try {
                        data = null;
                        data = m.group(1).substring(m.group(1).indexOf("(Local) ") + 8).trim();
                    } catch (Exception ex) {
                        ex.printStackTrace();                        
                    }
                    if(Main.isDebug()) {
                        System.out.println("[Studio Upstairs (data)]: \"" + data + "\"");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
    }
    
    public String getData() {
        try {        
            if(data == null) {
                return null;
            }                
            return Utilities.capitalizeFirstLettersTokenizer(data.toLowerCase());  
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
