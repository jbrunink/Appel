/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 * @author Jurgen
 */
public class CustomExceptionHandler implements UncaughtExceptionHandler {
    
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught exception by " + t + ":");
        e.printStackTrace();
    }
    
}
