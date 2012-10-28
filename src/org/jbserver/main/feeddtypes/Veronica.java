/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.TimerTask;
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
public class Veronica implements Runnable {
    
    String title;
    String artist;
    String feedurl;
    
    public void run() {
        parse();
        if(Main.isDebug()) {
            System.out.println("\"" + this.getData() + "\"");
            System.out.println("Run called!");
        }
    }
    
    public Veronica() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./config.cfg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.feedurl = properties.getProperty("veronicafeed");
    }

    public void parse() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document feed = docBuilder.parse(Utilities.getInputStream(this.feedurl + "?" + System.currentTimeMillis()));
            
            try {
                title = null;
                title = ((Element) ((Element) ((Element) feed.getElementsByTagName("cuepoint").item(0)).getElementsByTagName("attributes").item(0)).getElementsByTagName("attribute").item(0)).getFirstChild().getNodeValue();
                if(Main.isDebug()) {
                    System.out.println("[Veronica (title)]: \"" + title + "\"");
                }
            } catch (NullPointerException ex) {
                System.out.println("Geen title in de XML gevonden!");
            } catch (Exception ex) {
                
            }
            
            try {
                artist = null;
                artist = ((Element) ((Element) ((Element) feed.getElementsByTagName("cuepoint").item(0)).getElementsByTagName("attributes").item(0)).getElementsByTagName("attribute").item(4)).getFirstChild().getNodeValue();
                if(Main.isDebug()) {
                    System.out.println("[Veronica (artist)]: \"" + artist + "\"");
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
            if(artist != null && artist.contains("AUDIOLINK") && artist.contains("copy of") && artist.startsWith("***")) {
                return null;
            }
            if(title != null && title.contains("AUDIOLINK") && title.contains("copy of") && title.startsWith("***")) {
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
