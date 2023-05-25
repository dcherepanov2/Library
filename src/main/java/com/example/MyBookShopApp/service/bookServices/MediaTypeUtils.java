package com.example.MyBookShopApp.service.bookServices;

import javax.servlet.ServletContext;

import com.example.MyBookShopApp.dto.ExtensionFileTypeDetector;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MediaTypeUtils {

    private MediaTypeUtils(){

    }
    // abc.zip
    // abc.pdf,..
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        try {
            return MediaType.parseMediaType(servletContext.getMimeType(fileName));
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public static Integer getTypeFromMediaTypeToInteger(File file){
        Path filePath = Paths.get(file.getAbsolutePath());
        try {
            return Integer.valueOf(new ExtensionFileTypeDetector().probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTypeFromMediaTypeToString(File file){
        Path filePath = Paths.get(file.getAbsolutePath());
        try {
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                return new ExtensionFileTypeDetector().probeContentType(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
