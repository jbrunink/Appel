/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jurgen and some other guy, I dont remember his name anymore. :-(
 */
public class CustomScheduledExecutor extends ScheduledThreadPoolExecutor {
    
    public CustomScheduledExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return super.scheduleAtFixedRate(wrapRunnable(command), initialDelay, period, unit);
    }
    public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return super.scheduleWithFixedDelay(wrapRunnable(command), initialDelay, delay, unit);
    }
    private Runnable wrapRunnable(Runnable command) {
        return new LogOnExceptionRunnable(command);
    }
    
    private class LogOnExceptionRunnable implements Runnable {
        private Runnable theRunnable;
        
            public LogOnExceptionRunnable(Runnable theRunnable) {
                super();
                this.theRunnable = theRunnable;
            }

            public void run() {
                try {
                    theRunnable.run();
                } catch (Exception e) {
                    System.err.println("error in executing: " + theRunnable + ". It will no longer be run!");
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            } 
    }   
}