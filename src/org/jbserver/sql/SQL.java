/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.sql;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Jurgen
 */
public class SQL {
        
    public static Connection initConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./config.cfg"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        String mysqlhost = properties.getProperty("mysqlhost");
        String mysqluser = properties.getProperty("mysqluser");
        String mysqlpass = properties.getProperty("mysqlpass");
        String mysqldb = properties.getProperty("mysqldb");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return DriverManager.getConnection("jdbc:mysql://"+mysqlhost+"/"+mysqldb+"?user="+mysqluser+"&password=" + mysqlpass);        
    }
    
    public static Boolean execute(Statement stmt, String query) throws SQLException {
        System.out.println("[MySQL] " + query);
        return stmt.execute(query);                
    }
    
}
