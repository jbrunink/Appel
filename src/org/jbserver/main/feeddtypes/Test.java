/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbserver.main.feeddtypes;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jurgen
 */
public class Test {
    
    public Test() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document DrieFMfeed = docBuilder.parse(new File("./dalet.xml"));

        System.out.println(((Element) DrieFMfeed.getElementsByTagName("Current").item(0)).getElementsByTagName("titleName").item(0).getChildNodes().item(0).getNodeValue());
        System.out.println(((Element) DrieFMfeed.getElementsByTagName("Current").item(0)).getElementsByTagName("artistName").item(0).getChildNodes().item(0).getNodeValue());
    }
        
}
