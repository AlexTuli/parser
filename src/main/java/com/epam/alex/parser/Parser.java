package com.epam.alex.parser;

import com.epam.alex.exception.ParserException;
import com.epam.alex.model.Element;

import java.io.InputStream;

/**
 * Created by Alexander on 2/21/2016.
 */
public interface Parser {

    Element parse(InputStream inputStream) throws ParserException;

}
