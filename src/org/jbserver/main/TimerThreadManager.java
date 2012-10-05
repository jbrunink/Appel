/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.util.TimerTask;
import org.jbserver.main.feeddtypes.DrieFM;

/**
 *
 * @author Jurgen
 */
public class TimerThreadManager extends TimerTask {
    
    Umbrella umbrella;
    
    DrieFM driefm;
    
    public TimerThreadManager(Umbrella umbrella) {
        this.umbrella = umbrella;        
    }
        
    public void run() {
        
    }    
}
