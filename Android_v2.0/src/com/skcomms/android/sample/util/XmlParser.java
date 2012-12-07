package com.skcomms.android.sample.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XmlParser {
	
	private final String TAG = getClass().getName();

	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setCoalescing(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}
		return doc;
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
	public final ArrayList<HashMap<String, String>> getparseData(String xml , String itemKey , String[] keyList){
		ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
		
		Document doc = getDomElement(xml);
		NodeList nl = doc.getElementsByTagName(itemKey);
		
		for (int i = 0; i < nl.getLength(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);

			for (int index = 0; index < keyList.length; index++) {
				map.put(keyList[index], getValue(e, keyList[index]));
			}
			itemsList.add(map);

			Log.d(TAG, "itemsList : " + map);
		}
		return itemsList;
	}
}
