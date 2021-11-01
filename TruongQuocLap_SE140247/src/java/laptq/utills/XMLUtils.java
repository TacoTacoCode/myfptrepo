/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptq.utills;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Taco
 */
public class XMLUtils {
        public static Document parseFileToDom(String filePath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(filePath);
        Document doc = db.parse(file);
        return doc;
    }

    public static XPath createXPath() throws Exception {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        return xPath;
    }

    public static boolean transformDOMtoResult(Node node, String filePath) throws Exception {
        Source src = new DOMSource(node);
        File file = new File(filePath);
        Result result = new StreamResult(file);
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();
        trans.transform(src, result);
        return true;
    }

    
        public static void parseFileWithSAX(String filePath, DefaultHandler handler) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sax = spf.newSAXParser();
        File file = new File(filePath);
        sax.parse(file, handler);
    }
}
