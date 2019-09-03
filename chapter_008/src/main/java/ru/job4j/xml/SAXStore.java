package ru.job4j.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provides storing sum of entries' field value
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-26
 */
public class SAXStore {
    private final SAXParser parser;
    private final SAXHandler handler;

    public SAXStore() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            parser = saxParserFactory.newSAXParser();
            handler = new SAXHandler();
        } catch (ParserConfigurationException | SAXException e) {
            throw new IllegalStateException("error occurs due SAX parser initialization", e);
        }
    }

    /**
     * Checks field sum in xml file
     * @param in input stream with xml file
     * @return sum of field numbers
     */
    public long count(InputStream in) {
        try {
            parser.parse(in, handler);
            return handler.counter;
        } catch (SAXException | IOException e) {
            throw new IllegalStateException("cannot parse file", e);
        }
    }

    private static class SAXHandler extends DefaultHandler {
        private long counter;

        @Override
        public void startDocument() throws SAXException {
            counter = 0;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("entry")) {
                counter += Integer.valueOf(attributes.getValue("field"));
            }
        }
    }
}