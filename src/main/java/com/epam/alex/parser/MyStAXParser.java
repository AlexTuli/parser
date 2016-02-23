package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
import com.epam.alex.model.Element;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.*;

/**
 * Created on 2/23/2016.
 *
 * @author Bocharnikov Alexander
 */
public class MyStAXParser implements Parser {

    private static final Logger log = Logger.getLogger(MyStAXParser.class);
    private StringBuilder content;
    private Deque<Element> elementDeque;
    private Element currentElement;

    public MyStAXParser() {
        elementDeque = new ArrayDeque<>();
    }

    @Override
    public Element parse(InputStream inputStream) throws ParserException {
        log.info("Start to parsing");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = factory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                processEvent(xmlEventReader.nextEvent());
            }
        } catch (XMLStreamException ignored) {
            log.error("Exception in StAX parser");
        }
        log.info("Parsing is over");
        return currentElement;
    }

    private void processEvent(XMLEvent xmlEvent) {
        switch (xmlEvent.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                processStartElement(xmlEvent);
                break;
            case XMLStreamConstants.CHARACTERS:
                if (content != null) {
                    content.append(xmlEvent.asCharacters().getData());
                }
                break;
            case XMLStreamConstants.END_ELEMENT:
                processEndElement();
                break;
        }
    }

    private void processEndElement() {
        if (content != null && content.length() > 0) {
            String temp = content.toString().trim();
            currentElement.setContent(temp);
            content = null;
        }
        if (elementDeque.peekLast() != null) {
            elementDeque.peekLast().addChildElement(currentElement);
            currentElement = elementDeque.pollLast();
        }
    }

    private void processStartElement(XMLEvent xmlEvent) {
        if (currentElement != null) {
            elementDeque.add(currentElement);
        }
        StartElement startElement = xmlEvent.asStartElement();
        currentElement = new Element();
        extractAndSetAttributes(startElement);
        currentElement.setName(startElement.getName().toString());
        content = new StringBuilder();
    }

    @SuppressWarnings(value = "unchecked")
    private void extractAndSetAttributes(StartElement startElement) {
        Iterator<Attribute> attributeIterator = startElement.getAttributes();
        Map<String, String> result = null;
        while (attributeIterator.hasNext()){
            result = new HashMap<>();
            Attribute next = attributeIterator.next();
            result.put(next.getName().toString(),next.getValue());
        }
        currentElement.setAttributes(result);
    }
}
