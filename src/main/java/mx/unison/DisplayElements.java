package mx.unison;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
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

    public DisplayElements() {
        super();
        inName = false;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("INICIO DEL DOCUMENTO XML\n");
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        System.out.printf("Inicio de elemento: %s\n", qName);
        if ( qName.equals("name") ) {
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
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("name")) {
            inName = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (inName) {
             String name = new String(ch, start, length);
            System.out.printf("\t%s\n",name);
        }
    }

    public void endDocument() throws SAXException {
        System.out.println("FIN DE DOCUMENTO XML\n");
    }


    public void warning(SAXParseException e) throws SAXException {
        LOG.warning( e.getMessage() );
    }
    public void error(SAXParseException e) throws SAXException {
        LOG.severe( e.getMessage() );
        throw e;
    }

    public void fatalError(SAXParseException e) throws SAXException {
        LOG.severe( e.getMessage() );
        throw e;
    }

    static void main(String[] args) {
        if (args.length == 0) {
            LOG.severe("No se especifica el documento del XML");
            System.exit(1);
        }

        // definir objeto para configurar parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);


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