package mx.unison;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DisplayElements extends DefaultHandler {
    private static final String CLASS_NAME =
            DisplayElements.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    // entramos al elemento <name> ?
    private boolean inName = false;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        System.out.printf("Inicio de elemento: %s\n", qName);
        if (localName.equals("name")) {
            inName = true;
        }
        if( attributes.getLength() > 0 ) {
            System.out.printf("Lista de atributos de: %s\n", qName);
            for( int i = 0; i < attributes.getLength(); i++ ) {
                System.out.printf("\t%s = %s\n", attributes.getQName(i), attributes.getValue(i));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        inName = false;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inName) {
            String name = new String(ch, start, length);
            System.out.printf("\t%s\n",name);
        }
    }

    static void main(String[] args) {
        if (args.length == 0) {
            LOG.severe("No se especifica el documento del XML");
            System.exit(1);
        }

        // definir objeto para configurar parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        SAXParser saxParser = null;
        try {
            // crear parser con las propiedades solicitadas
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOG.severe(e.getMessage());
        }

        File xmlDocument = new File(args[0]);
        DisplayElements handler = new DisplayElements();
        try {
            saxParser.parse( xmlDocument, handler );
        } catch (SAXException e) {
            LOG.info(e.getMessage());
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }

    }


}