package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.service.bookServices.MediaTypeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MediaTypeUtilsTest {

    @Autowired
    private ServletContext servletContext;

    @Test
    public void pdfUtilsTest(){
        assertEquals(MediaTypeUtils.getMediaTypeForFileName(servletContext,"123.pdf"), MediaType.APPLICATION_PDF);
    }

    @Test
    public void epubUtilsTest(){
        assertEquals(MediaTypeUtils.getMediaTypeForFileName(servletContext,"123.epub").toString(), "application/epub+zip");
    }

    @Test
    public void fb2UtilsTest(){
        assertEquals(MediaTypeUtils.getMediaTypeForFileName(servletContext,"123.fb2"), MediaType.APPLICATION_OCTET_STREAM);
    }
}
