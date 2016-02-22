package com.epam.alex;

import com.epam.alex.exception.FileUploadException;
import com.epam.alex.exception.ParserException;
import com.epam.alex.exception.RequestException;
import com.epam.alex.io.FileReader;
import com.epam.alex.io.FileUploader;
import com.epam.alex.model.Element;
import com.epam.alex.parser.MyDomParser;
import com.epam.alex.parser.MySaxParser;
import com.epam.alex.parser.ParserFactory;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/sax")
    public @ResponseBody Element sayHello(@RequestParam("file") MultipartFile file) throws RequestException {
        log.debug("Request received.");
        FileReader reader = new FileUploader();
        try {
            InputStream stream = reader.readFile(file);
            MySaxParser saxParser = ParserFactory.getInstance().getMySaxParser();
            return saxParser.doParse(stream);
        } catch (FileUploadException e) {
            log.error("File upload exception error");
            throw new RequestException(e);
        } catch (ParserException e) {
            log.error("Parser exception");
            throw new RequestException(e);
        }
    }

}
