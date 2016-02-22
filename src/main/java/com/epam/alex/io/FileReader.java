package com.epam.alex.io;

import com.epam.alex.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created on 2/22/2016.
 *
 * @author Bocharnikov Alexander
 */
public interface FileReader {

    InputStream readFile(MultipartFile file) throws FileUploadException;

}
