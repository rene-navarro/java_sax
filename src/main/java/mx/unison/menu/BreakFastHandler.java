package mx.unison.menu;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BreakFastHandler extends DefaultHandler {
    private static final String CLASS_NAME = BreakFastHandler.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private boolean name;
    private boolean price;
    private boolean description;
    private boolean calories;

    private Food fooItem;
    private ArrayList<Food> lista;

    public BreakFastHandler() {
        super();

        lista = new ArrayList<>();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            LOG.severe("No se especifica el documento del XML");
            return;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOG.severe(e.getMessage());
        }

        File xmlFile = new File(args[0]);

        BreakFastHandler handler = new BreakFastHandler();

        try {
            saxParser.parse(xmlFile, handler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }

        ArrayList<Food> lista = handler.getLista();

        for (Food food : lista) {
            System.out.println(food.toString());
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //LOG.info("startElement");

        if (localName.equals("food")) {
            fooItem = new Food();
        }
        if (localName.equals("name"))
            name = true;
        if (localName.equals("price"))
            price = true;
        if (localName.equals("description"))
            description = true;
        if (localName.equals("calories"))
            calories = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // LOG.info("characters");
        String contenido = new String(ch, start, length);

        if (name) {
            fooItem.setName(contenido);
        } else if (price) {
            fooItem.setPrice(Double.parseDouble(contenido));
        } else if (description) {
            fooItem.setDescription(contenido);
        } else if (calories) {
            fooItem.setCalories(Integer.parseInt(contenido));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //LOG.info( qName );
        if (localName.equals("name"))
            name = false;
        if (localName.equals("price"))
            price = false;
        if (localName.equals("description"))
            description = false;
        if (localName.equals("calories"))
            calories = false;

        if (localName.equals("food")) {
            //System.out.println( fooItem.toString() ) ;
            lista.add(fooItem);
        }
    }

    public ArrayList<Food> getLista() {
        return lista;
    }
}
