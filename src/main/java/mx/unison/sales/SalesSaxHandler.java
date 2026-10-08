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

public class SalesSaxHandler extends DefaultHandler {
    private static final String CLASS_NAME = SalesSaxHandler.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private SAXParser parser = null;
    private SAXParserFactory spf;

    private double totalSales;
    private boolean inSales;

    public SalesSaxHandler() {
        super();
        spf = SAXParserFactory.newInstance();
        // verificar espacios de nombre
        spf.setNamespaceAware(true);
        // validar que el documento este bien formado (well formed)
        spf.setValidating(true);
    }

    private void process(File file) {
        try {
            // obtener un parser para verificar el documento
            parser = spf.newSAXParser();
            LOG.info("Parser object is: " + parser);
        } catch (SAXException | ParserConfigurationException e) {
            LOG.severe(e.getMessage());
            System.exit(1);
        }
        System.out.println("\nStarting parsing of " + file + "\n");
        try {
            // iniciar analisis del documento
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
    public void endDocument() throws SAXException {
        // Se proceso todo el documento, imprimir resultado
        System.out.printf("Ventas totales: $%,8.2f\n", totalSales);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Elemento actual que se esta procesando
        if( localName.equals("sales") )        {
            //  <sales>: entramos al elemento
            inSales = true;
        }
    }

    @Override
    public void characters(char[] bytes, int start, int length) throws SAXException {
        if( inSales ) { // Si estamos en el elemento <sales> :
            // obtener el contenido del elemento
            String salesValue = new String(bytes, start, length);
            double val = 0.0;
            try {
                if(salesValue.startsWith("$")) {
                    salesValue = salesValue.substring(1);
                }
                val = Double.parseDouble(salesValue);
            } catch (NumberFormatException e) {
                LOG.severe(e.getMessage());
            }
            System.out.printf("$%,8.2f\n",val);
            totalSales = totalSales + val;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if( localName.equals("sales") )        {
            // </sales>: salimos de elemento
            inSales = false;
        }
    }


    public static void main(String args[]) {
        if (args.length == 0) {
            LOG.severe("No se especifica el documento del XML");
            return;
        }
        File xmlFile = new File(args[0]);
        SalesSaxHandler handler = new SalesSaxHandler();
        handler.process(xmlFile);
    }
}
