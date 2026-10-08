package mx.unison.html;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Outliner {
    public static void main(String[] args) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(false);

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XHTMLHandler handler = new XHTMLHandler();
            saxParser.parse(new File("xml_standard.html"), handler);


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
