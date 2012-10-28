/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

/**
 *
 * @author Jurgen
 */
public class Utilities {
    
    public static String capitalizeFirstLettersTokenizer ( String s ) {

        final StringTokenizer st = new StringTokenizer( s, " ", true );
        final StringBuilder sb = new StringBuilder();

        while ( st.hasMoreTokens() ) {
            String token = st.nextToken();
            token = String.format( "%s%s",
                                    Character.toUpperCase(token.charAt(0)),
                                    token.substring(1) );
            sb.append( token );
        }

        return sb.toString();

    }
    
    public static String sendPost(String url, String postData) throws MalformedURLException, IOException {

        // Send data
        URL urlObj = new URL(url);
        URLConnection conn = urlObj.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(postData);
        wr.flush();

        // Get the response
        InputStream input = conn.getInputStream();
        StringBuffer buffer = new StringBuffer();
        byte[] b = new byte[1024];
        int a = 0;
        while ((a = input.read(b)) > 0) {
            buffer.append(new String(b, 0, a));
        }
        wr.close();
        input.close();
        return buffer.toString();
    }

    public static String sendGet(String url) throws MalformedURLException, IOException {
        URL urlObj = new URL(url);
        URLConnection conn = urlObj.openConnection();
        
        InputStream input = conn.getInputStream();
        StringBuffer buffer = new StringBuffer();
        byte[] b = new byte[1024];
        int a = 0;
        while ((a = input.read(b)) > 0) {
            buffer.append(new String(b, 0, a));
        }
        input.close();
        return buffer.toString();
    }
    
    public static String sendGet(String url, Boolean headerenable) throws MalformedURLException, IOException {
        URL urlObj = new URL(url);
        URLConnection conn = urlObj.openConnection();
        if(headerenable == true) {
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) Gecko/20100101 Firefox/11.0");
        }        
        InputStream input = conn.getInputStream();
        StringBuffer buffer = new StringBuffer();
        byte[] b = new byte[1024];
        int a = 0;
        while ((a = input.read(b)) > 0) {
            buffer.append(new String(b, 0, a));
        }
        input.close();
        return buffer.toString();
    }
    
    public static InputStream getInputStream(String sURL) throws MalformedURLException, IOException
    {
        URL url = new URL(sURL);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        return conn.getInputStream();
    }
   
}
