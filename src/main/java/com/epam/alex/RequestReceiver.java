package com.epam.alex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2/17/2016.
 *
 * @author Bocharnikov Alexander
 */
@RestController
public class RequestReceiver {

    @RequestMapping("/sax")
    public @ResponseBody HelloWorld sayHello(){
        System.out.println("\n\nSax parser is was called!\n");
        return new HelloWorld("Hello, world!");
    }

}
