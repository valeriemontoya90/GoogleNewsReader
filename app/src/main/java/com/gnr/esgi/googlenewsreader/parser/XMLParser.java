package com.gnr.esgi.googlenewsreader.parser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Ismail on 13-12-2015.
 */
public class XMLParser {
    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return xml;
    }

    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();

            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        }
        catch(ParserConfigurationException
                | SAXException
                | IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public final String getElementValue(Node elem) {
        Node child;

        if(elem != null)
            if(elem.hasChildNodes())
                for(child = elem.getFirstChild(); child != null; child = child.getNextSibling())
                    if(child.getNodeType() == Node.TEXT_NODE)
                        return child.getNodeValue();

        return new String();
    }

    public String getValue(Element item, String str) {
        return this.getElementValue(
                item.getElementsByTagName(str)
                        .item(0));
    }
}
