package com.epam.alex.io;

import com.epam.alex.exception.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created on 2/12/2016.
 *
 * @author Bocharnikov Alexander
 */
//@Controller
public class FileUploader implements FileReader{

    private static final Logger log = Logger.getLogger(FileUploader.class);

//    @RequestMapping(value="/upload", method=RequestMethod.GET)
//    public @ResponseBody String provideUploadInfo() {
//        return "You can upload a file by posting to this same URL.";
//    }

//    @RequestMapping(value="/upload", method=RequestMethod.POST)
//    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
//                                                 @RequestParam("file") MultipartFile file){
//        if (!file.isEmpty()) {
//            try {
////                byte[] bytes = file.getBytes();
//                InputStream inputStream = file.getInputStream();
////                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getName())));
////                stream.write(bytes);
////                stream.close();
//                return "You successfully uploaded " + name + "!";
//            } catch (IOException e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + name + " because the file was empty.";
//        }
//    }

    public InputStream readFile (MultipartFile file) throws FileUploadException {
        if (!file.isEmpty()) {
            try {
                return file.getInputStream();
            } catch (IOException e) {
                log.error("Can't read file");
                throw new FileUploadException(e);
            }
        }
        log.error("Can't read file");
        throw new FileUploadException("Error by reading file in FileUpload");
    }
}
