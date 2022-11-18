package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.configuration.MockitoApplicationContext;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.resourcesrepos.ResourceRepo;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ResourceStorageServiceTest {

    @Spy
    private MockitoApplicationContext mockitoApplicationContext;
    private ResourceStorage resourceStorage;

    private static String valueUpload;

    private Book book;
    private CommonsMultipartFile multipartFile;

    @SneakyThrows
    @BeforeEach
    public void init(){
        resourceStorage = (ResourceStorage) mockitoApplicationContext.getBean(ResourceStorage.class);
        valueUpload = (String) mockitoApplicationContext.getFieldTestClassByName("valueUpload", ResourceStorage.class);
        book = (Book) mockitoApplicationContext.getFieldTestClassByName("book", ResourceStorage.class);
        multipartFile = (CommonsMultipartFile) mockitoApplicationContext.getFieldTestClassByName("multipartFile", ResourceStorage.class);
    }

    @Test
    public void saveNewBookImage() throws IOException {
        resourceStorage.saveNewBookImage(multipartFile, book.getSlug());
        File file = new File(valueUpload+"820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg");
        assertNotNull(file);
    }

    @SneakyThrows
    @AfterAll
    public static void deleteFile(){
        Files.deleteIfExists(Paths.get(valueUpload+"820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg"));
    }
}
