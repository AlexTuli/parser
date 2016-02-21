package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2/21/2016.
 *
 * @author Alexander Bocharnikov
 */
public class MyDomParser implements Parser{
    //todo TEST IT!
    @Override
    public com.epam.alex.model.Element doParse(InputStream inputStream) throws ParserException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ignored) {
            throw new ParserException();
        }
        Document doc;
        try {
            doc = dBuilder.parse(inputStream);
        } catch (SAXException | IOException e) {
            throw new ParserException();
        }
        Element rootElement = doc.getDocumentElement();// Returns the root element
        com.epam.alex.model.Element rootElem = new com.epam.alex.model.Element();
        rootElem.setName(rootElement.getNodeName());
        rootElem.setAttributes(parseAttributes(rootElement)); // Get all attributes
        NodeList childNodes = rootElement.getChildNodes();
        if (childNodes.getLength() > 0) {
            for (int i = 0 ; i < childNodes.getLength(); i++){
                rootElem.addChildElement(parseChildNode(childNodes.item(i)));
            }
        }
        return rootElem;
        }

    private com.epam.alex.model.Element parseChildNode(Node node) {
        com.epam.alex.model.Element element;
        element = new com.epam.alex.model.Element();
        element.setName(node.getNodeName()); // Name of the Node
        element.setAttributes(parseAttributes(node)); // Attributes of the Node
        element.setContent(node.getTextContent()); // Content of the Node
        int length = node.getChildNodes().getLength();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                element.addChildElement(parseChildNode(node.getChildNodes().item(i)));
            }
        }
        return element;
    }

    private Map<String, String> parseAttributes(Node item) {
        NamedNodeMap attributes = item.getAttributes();
        Map <String, String> result = new HashMap<>();
        for(int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            result.put(attribute.getNodeName(), attribute.getNodeValue());
        }
        return result;
    }


}
