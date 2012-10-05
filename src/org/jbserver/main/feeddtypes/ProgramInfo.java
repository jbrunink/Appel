/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimerTask;
import org.jbserver.main.Main;
import org.jbserver.sql.SQL;

/**
 *
 * @author Jurgen
 */
public class ProgramInfo implements Runnable {
    
    private Boolean validfirstrun = false;
    
    private String[] strDays = new String[] { "zondag", "maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag" };
    
    private String sDrieFMBroadcaster;
    private String sDrieFMProgram;
    
    private String sVeronicaProgram;
    
    public Boolean hasRun() {
        return this.validfirstrun;
    }
    
    public String getData(String what) {
        try {
            if(what == null) {
                return null;
            }

            if(what.equalsIgnoreCase("3fm") && (sDrieFMProgram != null && sDrieFMBroadcaster != null)) {
                return sDrieFMProgram + " (" + sDrieFMBroadcaster + ")";
            }

            if(what.equalsIgnoreCase("veronica") && (sVeronicaProgram != null)) {
                return sVeronicaProgram;
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }        
    }
        
    public void run() {
        try {
            Calendar now = Calendar.getInstance();
            Connection connection = null;
            Statement statement = null;
            ResultSet resultset = null;
            
            int h = now.get(Calendar.DAY_OF_WEEK);

            System.out.println(strDays[h - 1]);

            try {
                connection = SQL.initConnection();
                statement = connection.createStatement();
                if(SQL.execute(statement, "SELECT `naam`,`omroep` FROM `programmatabel` WHERE `station` = '3FM' AND `dag` = '" + strDays[now.get(Calendar.DAY_OF_WEEK) - 1] + "' AND `uur` = '" + now.get(Calendar.HOUR_OF_DAY) + "'")) {
                    resultset = statement.getResultSet();
                    if(resultset.next()) {
                        sDrieFMBroadcaster = resultset.getString("omroep");
                        sDrieFMProgram = resultset.getString("naam");
                        if(Main.isDebug()) {
                            System.out.println("[Programma Informatie (3FM)] " + sDrieFMProgram + " (" + sDrieFMBroadcaster + ")");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();            
            } finally {
                if(resultset != null) {
                    try {
                        resultset.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(statement != null) {
                    try {
                        statement.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
            //Veronica
            
            try {
                connection = SQL.initConnection();
                statement = connection.createStatement();
                if(SQL.execute(statement, "SELECT `naam`,`omroep` FROM `programmatabel` WHERE `station` = 'VERONICA' AND `dag` = '" + strDays[now.get(Calendar.DAY_OF_WEEK) - 1] + "' AND `uur` = '" + now.get(Calendar.HOUR_OF_DAY) + "'")) {
                    resultset = statement.getResultSet();
                    if(resultset.next()) {
                        sVeronicaProgram = resultset.getString("naam");
                        if(Main.isDebug()) {
                            System.out.println("[Programma Informatie (Veronica)] " + sVeronicaProgram);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();            
            } finally {
                if(resultset != null) {
                    try {
                        resultset.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(statement != null) {
                    try {
                        statement.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        this.validfirstrun = true;
        
    }
    
}
