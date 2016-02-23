package com.epam.alex;

import com.epam.alex.exception.FileUploadException;
import com.epam.alex.exception.ParserException;
import com.epam.alex.exception.RequestException;
import com.epam.alex.io.FileReader;
import com.epam.alex.io.FileUploader;
import com.epam.alex.model.Element;
import com.epam.alex.parser.MyDomParser;
import com.epam.alex.parser.MySaxParser;
import com.epam.alex.parser.MyStAXParser;
import com.epam.alex.parser.ParserFactory;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created on 2/17/2016.
 *
 * @author Bocharnikov Alexander
 */
@RestController
public class RequestReceiver {

    private static final Logger log = Logger.getLogger(RequestReceiver.class);
    public static final String SAX = "sax";
    public static final String DOM = "dom";
    public static final String STAX = "stax";

    @RequestMapping(value = {"/sax", "/stax", "/dom"}, method = RequestMethod.GET)
    public @ResponseBody String sayHello() {
        return "Please, upload the file";
    }

    @RequestMapping(value = {"/sax", "/stax", "/dom", "/parser"}, method = RequestMethod.POST)
    public @ResponseBody Element sayHello(@RequestParam("file") MultipartFile file,
                                          @RequestParam(value = "type") String parserType)
            throws RequestException {
        log.debug("Request received. Request param is " + parserType);
        FileReader reader = new FileUploader();
        try {
            InputStream stream = reader.readFile(file);
            ParserFactory factory = ParserFactory.getInstance();
            Element result = null;
            switch (parserType) {
                case SAX:
                    log.debug("SAX parser initialize");
                    MySaxParser mySaxParser = factory.getMySaxParser();
                    result = mySaxParser.parse(stream);
                    break;
                case DOM:
                    log.debug("DOM parser initialize");
                    MyDomParser myDomParser = factory.getMyDomParser();
                    result = myDomParser.parse(stream);
                    break;
                case STAX:
                    log.debug("StAX parser initialize");
                    MyStAXParser myStAXParser = factory.getMyStAXParser();
                    result = myStAXParser.parse(stream);
                    break;
            }
            return result;
        } catch (FileUploadException e) {
            log.error("File upload exception error");
            throw new RequestException(e);
        } catch (ParserException e) {
            log.error("Parser exception");
            throw new RequestException(e);
        }
    }

}
