package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
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

    public static final String SPACE_REGEX = "^\\s*$";

    @Override
    public com.epam.alex.model.Element parse(InputStream inputStream) throws ParserException {
        Element rootElement = null;// Returns the root element
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            rootElement = doc.getDocumentElement();
            rootElement.normalize();
        } catch (ParserConfigurationException | SAXException | IOException ignored) {
            //This is never happened :D
        } finally {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
        }
        if (rootElement != null) return parseRootElement(rootElement);
        else return null;
    }

    private com.epam.alex.model.Element parseRootElement(Element rootElement) {
        com.epam.alex.model.Element result;
        result = new com.epam.alex.model.Element();
        result.setName(rootElement.getNodeName());
        result.setAttributes(parseAttributes(rootElement)); // Get all attributes
        NodeList childNodes = rootElement.getChildNodes();
        if (childNodes.getLength() > 0) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                short nodeType = childNodes.item(i).getNodeType();
                if (nodeType != Element.TEXT_NODE && nodeType != Element.COMMENT_NODE) {
                    com.epam.alex.model.Element childNode = parseChildNode(childNodes.item(i));
                    if (childNode != null) {
                        result.addChildElement(childNode);
                    }
                }
            }
        }
        return result;
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
                if (currentNode.getNodeType() == Element.TEXT_NODE) {
                    String nodeTextContent = currentNode.getTextContent();
                    if (!nodeTextContent.isEmpty() && !nodeTextContent.matches(SPACE_REGEX)) {
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
