package com.epam.alex.parser;

/**
 * Created on 2/21/2016.
 *
 * @author Alexander Bocharnikov
 */
public class ParserFactory {

    private final static ParserFactory INSTANCE = new ParserFactory();

    public ParserFactory getInstance() {
        return INSTANCE;
    }

    public MyDomParser getMyDomParser() {
        return new MyDomParser();
    }

}
