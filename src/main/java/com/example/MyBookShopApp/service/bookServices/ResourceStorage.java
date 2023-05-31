package com.example.MyBookShopApp.service.bookServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.exception.BookFileException;
import com.example.MyBookShopApp.repo.bookrepos.BookFilesRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ResourceStorage {

    @Value("${upload.path}")
    private String valueUpload;

    private final ServletContext servlet;

    private final BookFilesRepo bookFilesRepo;



    private final BookRepo bookRepo;

    @Autowired
    public ResourceStorage(ServletContext servlet, BookFilesRepo bookFilesRepo, BookRepo bookRepo) {
        this.servlet = servlet;
        this.bookFilesRepo = bookFilesRepo;
        this.bookRepo = bookRepo;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void saveNewBookImage(MultipartFile image, String slug) throws IOException {
        String filename = image.hashCode() + UUID.randomUUID().toString() + ".jpg";
        Book book = bookRepo.findBookBySlug(slug);
        if (!image.isEmpty() && book != null &&
                (Objects.requireNonNull(image.getOriginalFilename()).contains(".jpg")
                        || Objects.requireNonNull(image.getOriginalFilename()).contains(".png")
                )) {
            if (!new File(valueUpload).exists()) {
                Files.createDirectories(Paths.get(filename));
            }
            Path path = Paths.get(valueUpload,filename);
            image.transferTo(path);
            book.setImage("/uploads/image/" + filename);
            bookRepo.save(book);
        }
        throw new FileNotFoundException("Файл не найден!");
    }

    public String findBookDownloadFile(String slug, final Integer type) throws BookException, BookFileException {
        Book bookBySlug = bookRepo.findBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
        BookFile file = bookBySlug.getBookFiles().stream()
                .filter(x -> x.getTypeId().equals(type))
                .findFirst()
                .orElse(null);
        if (file == null)
            throw new BookFileException(ErrorMessageResponse.FILE_NOT_FOUND_EXCEPTION.getName());
        return file.getPath();
    }

    public List<BookFile> createListBooksFiles(List<MultipartFile> bookFilesFromRequest, String pathUpload) throws IOException {
        List<BookFile> bookFiles = new ArrayList<>();
        for(MultipartFile x: bookFilesFromRequest){
            String filename = x.hashCode() + UUID.randomUUID().toString() + x.getOriginalFilename();
            if (!new File(pathUpload).exists()) {
                Files.createDirectories(Paths.get(filename));
            }
            Path path = Paths.get(pathUpload,filename);
            x.transferTo(path);
            BookFile file = new BookFile();
            file.setPath(pathUpload+"/"+filename);
            file.setHash(filename);
            Integer typeFromMediaType = MediaTypeUtils.getTypeFromMediaTypeToInteger(path.toFile());
            file.setTypeId(typeFromMediaType);
            bookFilesRepo.save(file);
            bookFiles.add(file);
        }
        return bookFiles;
    }
}
