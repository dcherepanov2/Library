package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.resourcesrepos.ResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ResourceStorage {

    @Value("${upload.path}")
    private String valueUpload;

    private final BookRepo bookRepo;
    private final ResourceRepo resourceRepo;

    @Autowired
    public ResourceStorage(BookRepo bookRepo, ResourceRepo resourceRepo) {
        this.bookRepo = bookRepo;
        this.resourceRepo = resourceRepo;
    }

    public void saveNewBookImage(MultipartFile image, String slug) throws IOException {
        String filename = image.hashCode()+ UUID.randomUUID().toString()+".jpg";
        Book book = bookRepo.findBookBySlug(slug);
        if(!image.isEmpty()&&book!=null&&
                (Objects.requireNonNull(image.getOriginalFilename()).contains(".jpg")
                ||Objects.requireNonNull(image.getOriginalFilename()).contains(".png")
            )){
            if(!new File(valueUpload).exists()){
                Files.createDirectories(Paths.get(filename));
            }
            Path path = Paths.get(valueUpload,filename);
            image.transferTo(path);
            book.setImage("/uploads/image/"+filename);
        }
        //TODO: написать ошибку если не найден файл
    }

    public String findBookDownloadFile(String slug, Integer type) {
        return resourceRepo.findPathBookBySlug(slug, type).getPath();
    }
}
