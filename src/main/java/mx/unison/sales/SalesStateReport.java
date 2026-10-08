package  mx.unison.sales;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SalesStateReport extends DefaultHandler {
    private static final String CLASS_NAME = SalesStateReport.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private SAXParser parser = null;
    private SAXParserFactory spf;

    private double totalSales;
    private boolean inSales;

    private String currentElement;

    private String id;
    private String name;
    private String lastName;
    private String sales;
    private String state;
    private String dept;

    private String keyword;

    public SalesStateReport() {
        super();
        spf = SAXParserFactory.newInstance();
        // verificar espacios de nombre
        spf.setNamespaceAware(true);
        // validar que el documento este bien formado (well formed)
        spf.setValidating(true);
    }

    private void process(File file, String state) {
        try {
            // obtener un parser para verificar el documento
            parser = spf.newSAXParser();
        } catch (SAXException | ParserConfigurationException e) {
            LOG.severe(e.getMessage());
            System.exit(1);
        }
        System.out.println("\nStarting parsing of " + file + "\n");
        try {
            // iniciar analisis del documento
            keyword = state;
            parser.parse(file, this);
        } catch (IOException | SAXException e) {
            LOG.severe(e.getMessage());
        }
    }

    @Override
    public void startDocument() throws SAXException {
        // al inicio del documento inicializar
        // las ventas totales
        totalSales = 0.0;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (localName.equals("sale_record")) {
            inSales = true;
        }
        currentElement = localName;
    }

    @Override
    public void characters(char[] bytes, int start, int length) throws SAXException {

        switch ( currentElement ) {
            case "id":
                this.id = new String(bytes, start, length);
                break;
            case "first_name":
                this.name = new String(bytes, start, length);
                break;
            case "last_name":
                this.lastName = new String(bytes, start, length);
                break;
            case "sales":
                this.sales = new String(bytes, start, length);
                break;
            case "state":
                this.state = new String(bytes, start, length);
                break;
            case "department":
                this.dept = new String(bytes, start, length);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ( localName.equals("sale_record") ) {
            if ( state.equals( keyword ) )
                printRecord();
            inSales = false;
        }
    }

    private void printRecord() {
        System.out.printf("%4.4s %-10.10s %-10.10s %9.9s %-10.10s %-15.15s\n",
                id,name,lastName,sales,state,dept);
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            LOG.severe("No file to process. Usage is:" + "\njava SalesStateReport <keyword>");
            return;
        }
       // File xmlFile = new File("D:\\data\\xml\\sales.xml");
        File xmlFile = new File("sales.xml");
        SalesStateReport handler = new SalesStateReport();
        handler.process(xmlFile, args[0]);
    }
}
