package com.epam.alex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2/17/2016.
 *
 * @author Bocharnikov Alexander
 */
@Controller
public class RequestReceiver {

    @RequestMapping("/sax")
    public @ResponseBody void sayHello(){
        System.out.println("\n\nSax parser is was called!\n");
    }

    @RequestMapping("/dom")
    public void saySay () {
        System.out.println("\n\nDOM parser is was called.\n");
    }

    @RequestMapping("/stax")
    public void saySayIt () {
        System.out.println("\n\nDOM parser is was called.\n");
    }

}
