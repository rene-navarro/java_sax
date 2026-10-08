package mx.unison.html;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XHTMLHandler extends DefaultHandler {

    boolean isH1;
    boolean isH2;
    boolean isH3;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       if( qName.equalsIgnoreCase("h1") ) {
           isH1 = true;
       }
        if( qName.equalsIgnoreCase("h1") ) {
            isH2 = true;
        }
        if( qName.equalsIgnoreCase("h3") ) {
            isH3 = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if( qName.equalsIgnoreCase("h1") ) {
            isH1 = false;
        }
        if( qName.equalsIgnoreCase("h1") ) {
            isH2 = false;
        }
        if( qName.equalsIgnoreCase("h3") ) {
            isH3 = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch, start, length).trim();
        str.replace("\n"," " );
        if( isH1 ) {
            System.out.println(str);
        }
        if( isH2 ) {
            System.out.println("\t"+ str);
        }
        if( isH3 ) {
            System.out.println("\t\t"+ str);
        }
    }
}
