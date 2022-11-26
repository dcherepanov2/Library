package com.example.MyBookShopApp.unit.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.resourcesrepos.ResourceRepo;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ResourceStorageServiceTest {

    private ResourceStorage resourceStorage;


    private static String valueUpload;

    private Book book;
    private CommonsMultipartFile multipartFile;

    @Value("${upload.path}")
    public void setValueUpload(String valueUpload){
        ResourceStorageServiceTest.valueUpload = valueUpload;
    }

    @SneakyThrows
    @BeforeEach
    public void init(){
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
        ResourceRepo resourceRepo = Mockito.mock(ResourceRepo.class);
        book = new Book();
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        FileItem fileItem =
                diskFileItemFactory.createItem("jpg",
                        "image/jpeg",
                        false,
                        "src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg");
        fileItem.getOutputStream();
        multipartFile = new CommonsMultipartFile(fileItem);
        Mockito.lenient().when(bookRepo.findBookBySlug(book.getSlug())).thenReturn(book);
        Mockito.lenient().when(bookRepo.save(book)).thenReturn(book);
        resourceStorage = new ResourceStorage(bookRepo,resourceRepo);
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
