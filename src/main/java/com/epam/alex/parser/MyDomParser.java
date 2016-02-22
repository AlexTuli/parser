package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
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
public class MyDomParser implements Parser {

    private static final Logger log = Logger.getLogger(MyDomParser.class);

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
        rootElement.normalize();
        com.epam.alex.model.Element rootElem = new com.epam.alex.model.Element();
        rootElem.setName(rootElement.getNodeName());
        rootElem.setAttributes(parseAttributes(rootElement)); // Get all attributes
        NodeList childNodes = rootElement.getChildNodes();
        if (childNodes.getLength() > 0) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                short nodeType = childNodes.item(i).getNodeType();
                if (nodeType != Element.TEXT_NODE && nodeType != Element.COMMENT_NODE) {
                    com.epam.alex.model.Element childNode = parseChildNode(childNodes.item(i));
                    if (childNode != null) {
                        rootElem.addChildElement(childNode);
                    }
                }
            }
        }
        return rootElem;
    }


    private com.epam.alex.model.Element parseChildNode(Node node) {
        if (node.getNodeType() == Element.COMMENT_NODE) return null;
        com.epam.alex.model.Element element = new com.epam.alex.model.Element();
        element.setName(node.getNodeName()); // Name of the Node
        element.setAttributes(parseAttributes(node)); // Attributes of the Node
        int length = node.getChildNodes().getLength();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                Node currentNode = node.getChildNodes().item(i);
                log.debug("Current node is " + currentNode.getNodeName());
                if (currentNode.getNodeType() == Element.TEXT_NODE) {
                    String nodeTextContent = currentNode.getTextContent();
                    if (!nodeTextContent.isEmpty() && !nodeTextContent.matches("^\\s*$")) {
                        log.debug("Content of the node is " + nodeTextContent);
                        element.setContent(nodeTextContent);
                    }

                } else {
                    com.epam.alex.model.Element childNode = parseChildNode(currentNode);
                    if (childNode != null) {
                        element.addChildElement(childNode);
                    }
                }
            }
        }
        return element;

    }

    private boolean isAText(Node node) {
        return node.getNodeName().equalsIgnoreCase("#text");
    }

    private Map<String, String> parseAttributes(Node item) {
        NamedNodeMap attributes = item.getAttributes();
        if (attributes != null) {
            Map<String, String> result = new HashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                result.put(attribute.getNodeName(), attribute.getNodeValue());
            }
            return result;
        }
        return null;
    }


}
