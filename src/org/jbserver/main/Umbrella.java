/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import org.jbserver.main.feeddtypes.*;

/**
 *
 * @author Jurgen
 */
public class Umbrella {
    
    HashMap bots = new HashMap();
    HashMap timers = new HashMap();
    
    CustomScheduledExecutor sexecutor;
    
    Timer timer = new Timer();
    
    CustomExceptionHandler ehandler;

    public AppelBot createBot(String name) {
        AppelBot bot = new AppelBot();
        bots.put(name, bot);
        return bot;
    }
    
    public AppelBot getBot(String name) {
        if(!bots.containsKey(name)) {
            return null;
        }
        return (AppelBot) bots.get(name);
    }
    
    public Collection<AppelBot> getBots() {
        return new ArrayList<AppelBot>(bots.values());
    }
    
    public Umbrella() {
        this.ehandler = new CustomExceptionHandler();        
    }
    
    public Object getTimer(String s) {
        return timers.get(s);
    }
    
    public void initTimers() {
        DrieFM driefm = new DrieFM();
        Veronica veronica = new Veronica();
        StudioUpstairs studioupstairs = new StudioUpstairs();
        FeedDataManager manager = new FeedDataManager(this);
        ProgramInfo pinfo = new ProgramInfo();
        News news = new News();
        news.addFeed("NOS", "http://");
        
        sexecutor = new CustomScheduledExecutor(6);
        
        sexecutor.scheduleWithFixedDelay(driefm, 5, 5, TimeUnit.SECONDS);
        sexecutor.scheduleWithFixedDelay(veronica, 5, 5, TimeUnit.SECONDS);
        sexecutor.scheduleWithFixedDelay(studioupstairs, 5, 5, TimeUnit.SECONDS);
        sexecutor.scheduleWithFixedDelay(pinfo, 5, 60, TimeUnit.SECONDS);
        sexecutor.scheduleWithFixedDelay(manager, 10, 5, TimeUnit.SECONDS);
        sexecutor.scheduleWithFixedDelay(news, 10, 5, TimeUnit.SECONDS);
        
        timers.put("driefm", driefm);             
        timers.put("veronica", veronica);  
        timers.put("studioupstairs", studioupstairs);  
        timers.put("manager", manager);  
        timers.put("pinfo", pinfo);
    }
}
