package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
import com.epam.alex.model.Element;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2/22/2016.
 *
 * @author Bocharnikov Alexander
 */
public class MySaxParser extends DefaultHandler implements Parser {

    private MyHandler myHandlerInstance;

    public MySaxParser() {
        myHandlerInstance = new MyHandler();
    }

    private static final Logger log = Logger.getLogger(MySaxParser.class);

    @Override
    public Element doParse(InputStream inputStream) throws ParserException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(inputStream, myHandlerInstance);
            return myHandlerInstance.getCurrentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Trouble in SAX parser");
            throw new ParserException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("Can't close input stream");
            }
        }
    }

    private class MyHandler extends DefaultHandler {

        private StringBuilder content;
        private Element currentElement;
        private Deque<Element> deque = new ArrayDeque<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (currentElement != null) {
                deque.add(currentElement);
            }
            currentElement = new Element();
            currentElement.setName(qName);
            extractAndSetAttributes(attributes);
            content = new StringBuilder();
        }

        /**
         * Extract and set attributes to <code>currentElement</code>
         */
        private void extractAndSetAttributes(Attributes attributes) {
            Map<String, String> result = null;
            for (int i = 0; i < attributes.getLength(); i++) {
                result = new HashMap<>();
                String attributeName = attributes.getQName(i);
                String attributeValue = attributes.getValue(i);
                result.put(attributeName, attributeValue);
            }
            currentElement.setAttributes(result);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (content != null) {
                content.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (content != null && content.length() > 0) {
                String temp = content.toString().trim();
                currentElement.setContent(temp);
                content = null;
            }
            if (deque.peekLast() != null) {
                deque.peekLast().addChildElement(currentElement);
                currentElement = deque.pollLast();
            }
        }

        private Element getCurrentElement() {
            return currentElement;
        }

    }
}


