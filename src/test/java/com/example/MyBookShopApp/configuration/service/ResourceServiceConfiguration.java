package com.example.MyBookShopApp.configuration.service;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.resourcesrepos.ResourceRepo;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Configuration
public class ResourceServiceConfiguration {

    @MockBean
    private ResourceRepo resourceRepo;

    private ResourceStorage resourceStorage;

    private static String valueUpload;

    private final Book book = new Book();
    private CommonsMultipartFile multipartFile;

    @Value("${upload.path}")
    public void setValueUpload(String valueUpload){
        ResourceServiceConfiguration.valueUpload = valueUpload;
    }

    @SneakyThrows
    public ResourceStorage initialize(){
        BookRepo bookRepo = Mockito.mock(BookRepo.class);
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
        return resourceStorage = new ResourceStorage(bookRepo,resourceRepo);
    }
}
