package com.epam.alex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2/21/2016.
 *
 * @author Alexander Bocharnikov
 */

public class Element {
    private String name;
    private Map<String, String> attributes;
    private List<Element> childElements;
    private String content;

    public Element(){
        childElements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<Element> getChildElements() {
        return childElements;
    }

    public void addChildElement(Element element){
        this.childElements.add(element);
    }

    public void setChildElements(List<Element> childElements) {
        this.childElements = childElements;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
