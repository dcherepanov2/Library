package org.example.services;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

public class MediaTypeUtils {

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

}