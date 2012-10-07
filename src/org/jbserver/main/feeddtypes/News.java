/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jurgen
 */
public class News implements Runnable {
    
    private HashMap<String, String> aFeeds;
    private HashMap<String, NewsFeed> aItems;
    private MessageDigest oMD5Instance;
    
    public News() {
        this.aFeeds = new HashMap<String, String>();
        this.aItems = new HashMap<String, NewsFeed>();
    }
    
    public void addFeed(String sFeedName, String sFeedURL) {
        this.aFeeds.put(sFeedName, sFeedURL);   
    }

    @Override
    public void run() {
        if(this.aFeeds.size() != this.aItems.size()) {
            System.out.println("ok");
        }                        
    }
    
}
