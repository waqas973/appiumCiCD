package com.qa.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestUtils {
   
	public static final long WAIT = 30;
	
	public HashMap<String , String> parseStringXML(InputStream file) throws Exception{
		
		HashMap<String , String> stringMap = new HashMap<String , String>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
	Document document = builder.parse(file);
		
		
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();
		
		
		NodeList nList = document.getElementsByTagName("string");
		
		  for(int temp=0; temp < nList.getLength(); temp++) {
			  Node node = nList.item(temp);
			  
			  if(node.getNodeType() == Node.ELEMENT_NODE) {
				  Element eElement = (Element) node ;
				  stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
			  }
		  }
		  
		  return stringMap;
	}
	
	public String getDateTime() {
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		  Date date = new Date();
	return 	  dateFormat.format(date);
	}
}
