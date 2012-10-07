/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.util.TimerTask;
import org.jbserver.main.feeddtypes.DrieFM;
import org.jbserver.main.feeddtypes.ProgramInfo;
import org.jbserver.main.feeddtypes.StudioUpstairs;
import org.jbserver.main.feeddtypes.Veronica;
import org.jbserver.pircbot.Colors;

/**
 *
 * @author Jurgen
 */
public class FeedDataManager implements Runnable {
    
    Umbrella umbrella;
    
    private String DrieFMDataOud;
    private String DrieFMData;
    private String DrieFMMeta;
    
    private String VeronicaDataOud;
    private String VeronicaData;
    private String VeronicaMeta;
    
    private String StudioUpstairsData;
    private String StudioUpstairsDataOud;
    
    private boolean validfirstrun = false;
    
    public FeedDataManager(Umbrella umbrella) {
        this.umbrella = umbrella;
    }
    
    public void run() {
        try {
            
            if(Main.disable()) return;
            
            
            if(umbrella.getTimer("pinfo") != null) {
                this.DrieFMMeta = ((ProgramInfo) umbrella.getTimer("pinfo")).getData("3fm");
                this.VeronicaMeta = ((ProgramInfo) umbrella.getTimer("pinfo")).getData("veronica");
                this.validfirstrun = ((ProgramInfo) umbrella.getTimer("pinfo")).hasRun();
            }
            if(umbrella.getTimer("driefm") != null) {
                
                if(this.validfirstrun == false) return;
                
                if(((DrieFM) umbrella.getTimer("driefm")).getData() != null) {
                    String s = ((DrieFM) umbrella.getTimer("driefm")).getData();
                    if(this.DrieFMData != null || !s.equalsIgnoreCase(DrieFMDataOud)) {
                        String append;
                        if((this.DrieFMMeta == null || this.DrieFMMeta.isEmpty())) {
                            append = "3FM: ";
                        } else {
                            append = Colors.BOLD + "3FM - " + this.DrieFMMeta + Colors.NORMAL + ": ";
                        }
                        for(AppelBot o : umbrella.getBots()) {
                            o.sendMessage("#3fm", append + s);
                        }
                        this.DrieFMDataOud = s;
                    }
                }
            }
            if(umbrella.getTimer("veronica") != null) {
                
                if(this.validfirstrun == false) return;
                
                if(((Veronica) umbrella.getTimer("veronica")).getData() != null) {
                    String s = ((Veronica) umbrella.getTimer("veronica")).getData();
                    if(this.VeronicaData != null || !s.equalsIgnoreCase(VeronicaDataOud)) {
                        String append;
                        if((this.VeronicaMeta == null || this.VeronicaMeta.isEmpty())) {
                            append = "Veronica: ";
                        } else {
                            append = Colors.BOLD + "Veronica - " + this.VeronicaMeta + Colors.NORMAL + ": ";
                        }
                        for(AppelBot o : umbrella.getBots()) {
                            o.sendMessage("#veronica", append + s);
                        }
                        this.VeronicaDataOud = s;
                    }
                }
            }
            if(umbrella.getTimer("studioupstairs") != null) {
                
                if(this.validfirstrun == false) return;
                
                if(((StudioUpstairs) umbrella.getTimer("studioupstairs")).getData() != null) {
                    String s = ((StudioUpstairs) umbrella.getTimer("studioupstairs")).getData();
                    if(this.StudioUpstairsData != null || !s.equalsIgnoreCase(StudioUpstairsDataOud)) { 
                            for(AppelBot o : umbrella.getBots()) {
                                if(o.isAllowedToMessage())
                                    o.sendMessage("#studioupstairs", Colors.BLUE + "Studio Upstairs: " + Colors.OLIVE + s);
                            }
                        this.StudioUpstairsDataOud = s;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
