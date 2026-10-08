package mx.unison;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.logging.Logger;

public class MyErrorHandler implements ErrorHandler {

    private static final String CLASS_NAME =
            MyErrorHandler.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    public MyErrorHandler() {
        super();
    }

    private String getParseExceptionInfo(SAXParseException spe) {
        String systemId = spe.getSystemId();
        if (systemId == null) {
            systemId = "null";
        }

        String info = "URI=" + systemId +
                " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
        return info;
    }

    public void warning(SAXParseException spe) throws SAXException {
        LOG.warning("Warning: " + getParseExceptionInfo(spe));
        System.out.printf("Warning: %s\n", spe.getMessage());

    }

    public void error(SAXParseException spe) throws SAXException {
        String message = "Error: " + getParseExceptionInfo(spe);
        System.out.println();
        LOG.severe(message);
        throw new SAXException(message);
    }

    public void fatalError(SAXParseException spe) throws SAXException {
        String message = "Fatal Error: " + getParseExceptionInfo(spe);
        System.out.println(message);
        LOG.severe(message);
        throw new SAXException(message);
    }
}
