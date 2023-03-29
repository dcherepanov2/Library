package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.dto.RecommendedBooksDto;
import com.example.MyBookShopApp.enums.ErrorMessageResponse;
import com.example.MyBookShopApp.exception.RecentBookException;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.bookServices.Book2UserService;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.bookServices.MediaTypeUtils;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;

import org.springframework.http.MediaType;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksApiController {

    @Value("${download.path}")
    private String PATH_DOWNLOAD;

    private final BookService bookService;
    private final ServletContext servlet;
    private final ResourceStorage resourceStorage;
    private final Book2UserService book2UserService;

    @Autowired
    public BooksApiController(BookService bookService, ServletContext servletContext, ResourceStorage resourceStorage, Book2UserService book2UserService) {
        this.bookService = bookService;
        this.servlet = servletContext;
        this.resourceStorage = resourceStorage;
        this.book2UserService = book2UserService;
    }

    @GetMapping("/recent")
    @ResponseBody
    public RecommendedBooksDto getFilterBooksRecent(
            @RequestParam(value = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") Date from,
            @RequestParam(value = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") Date to,
            @RequestParam(value = "offset") Integer offset,
            @RequestParam(value = "limit") Integer limit,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            @CookieValue(name = "keptContents", required = false) String keptContents,
            JwtUser jwtUser
    ) throws RecentBookException {
        if (to.before(from))
            throw new RecentBookException(ErrorMessageResponse.FILTER_PARAM_INCORRECT.getName());
        List<Book> books = bookService.getFilterBooksByDate(from, to, offset, limit);
        List<Book2UserEntity> allBookUser = book2UserService.getAllBook2User(jwtUser);
        if (cartContents != null || keptContents != null || allBookUser != null)
            return new RecommendedBooksDto(books, keptContents, cartContents, allBookUser);
        else
            return new RecommendedBooksDto(books);
    }

    @GetMapping("/tag/{slug}")
    public List<Book> getAllBooksByTag(
            @PathVariable("slug") String slug,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        return bookService.getAllBooksByTag(slug, offset, limit);
    }

    @GetMapping("/download/{slug}")
    public void downloadFile(
            HttpServletResponse response,
            @PathVariable("slug") String slug,
            @RequestParam("type") Integer type
    ) throws IOException {
        String fileName = resourceStorage.findBookDownloadFile(slug, type);
        if (fileName != null) {
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servlet, fileName);
            File file = new File(PATH_DOWNLOAD + "/" + fileName);
            // Content-Type
            // application/pdf
            response.setContentType(mediaType.getType());

            // Content-Disposition
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

            // Content-Length
            response.setContentLength((int) file.length());

            BufferedInputStream inStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
            BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
            inStream.close();
        } else
            throw new BadRequestException(ErrorMessageResponse.NOT_FOUND_BOOK.getName());
    }

    @GetMapping("/popular")
    public RecommendedBooksDto getPopularBooks(@RequestParam("offset") Integer offset,
                                               @RequestParam("limit") Integer limit,
                                               @CookieValue(name = "cartContents", required = false) String cartContents,
                                               @CookieValue(name = "keptContents", required = false) String keptContents,
                                               JwtUser jwtUser) {
        List<Book2UserEntity> allBookUser = book2UserService.getAllBook2User(jwtUser);
        if (cartContents != null || keptContents != null || allBookUser != null)
            return new RecommendedBooksDto(bookService.getPopularBooksDataApi(offset, limit), keptContents, cartContents, allBookUser);
        else
            return new RecommendedBooksDto(bookService.getPopularBooksDataApi(offset, limit));
    }
}