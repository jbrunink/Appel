/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.TimerTask;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jbserver.main.CustomExceptionHandler;
import org.jbserver.main.Main;
import org.jbserver.main.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jurgen
 */
public class DrieFM implements Runnable {
    
    private String title;
    private String artist;
    private String xmlurl;
    
    private CustomExceptionHandler ehandler = new CustomExceptionHandler();
    
    @Override
    public void run() {
        this.parse();
        if(Main.isDebug()) {
            System.out.println("\"" + this.getData() + "\"");
            System.out.println("Run called!");
        }
    }
    
    public DrieFM() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./config.cfg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.xmlurl = properties.getProperty("driefmxmlurl");
        Thread.currentThread().setUncaughtExceptionHandler(this.ehandler);   
    }

    public void parse() {        
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document DrieFMfeed = docBuilder.parse(Utilities.getInputStream(this.xmlurl));            
            try {
                title = null;
                title = ((Element) DrieFMfeed.getElementsByTagName("Current").item(0)).getElementsByTagName("titleName").item(0).getChildNodes().item(0).getNodeValue();
                if(Main.isDebug()) {
                    System.out.println("[3FM (title)]: \"" + title + "\"");
                }
            } catch (NullPointerException ex) {
                System.out.println("Geen title in de XML gevonden!");
            } catch (Exception ex) {
                
            }
            
            try {
                artist = null;
                artist = ((Element) DrieFMfeed.getElementsByTagName("Current").item(0)).getElementsByTagName("artistName").item(0).getChildNodes().item(0).getNodeValue();
                if(Main.isDebug()) {
                    System.out.println("[3FM (artist)]: \"" + artist + "\"");
                }
            } catch (NullPointerException ex) {
                System.out.println("Geen artist in de XML gevonden!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
    }
    
    public String getData() {
        try {
            if(artist == null || (artist.contains("AUDIOLINK") || artist.contains("copy of") || artist.startsWith("***"))) {
                return null;
            }
            if(title == null || (title.contains("AUDIOLINK") || title.contains("copy of") || title.startsWith("***"))) {
                return null;
            }
            if(artist == null && title == null) {
                return null;
            }                
            StringBuilder builder = new StringBuilder();
            if(this.artist != null) {
                builder.append(artist);
            }
            if(this.title != null) {
                if(this.artist != null) {
                    builder.append(" - ");
                }
                builder.append(title);
            }
            return Utilities.capitalizeFirstLettersTokenizer(builder.toString().toLowerCase());     
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
