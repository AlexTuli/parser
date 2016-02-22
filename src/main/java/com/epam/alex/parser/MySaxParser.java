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
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

/**
 * Created on 2/22/2016.
 *
 * @author Bocharnikov Alexander
 */
public class MySaxParser extends DefaultHandler implements Parser  {

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

    private class MyHandler extends  DefaultHandler {

        private Element currentElement;
        private Deque<Element> deque = new ArrayDeque<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            log.debug("This is start element: " + qName);
            if (currentElement != null) {
                deque.add(currentElement);
            }
            if (qName.equalsIgnoreCase("price")) {
                System.out.println("hello");
            }
            currentElement = new Element();
            currentElement.setName(qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length);
            content = content.trim();
            if (!content.isEmpty()) {
                log.debug("This is characters: " + new String(ch, start, length));
                currentElement.setContent(content);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            log.debug("This is end element: " + qName);
            if (deque.peekLast() != null) {
                deque.peekLast().addChildElement(currentElement);
                currentElement = deque.pollLast();
            }
        }

        protected Element getCurrentElement() {
            return currentElement;
        }

        @Override
        public void endDocument() throws SAXException {
            log.debug("Document is ended. We have next elements:\n" + currentElement.toString());
        }
    }
}


