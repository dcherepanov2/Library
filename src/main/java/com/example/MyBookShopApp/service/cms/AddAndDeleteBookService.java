package com.example.MyBookShopApp.service.cms;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.dto.BookChangeRequest;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookFilesRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.tagrepos.TagRepo;
import com.example.MyBookShopApp.service.bookServices.BookService;
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddAndDeleteBookService {

    @Value("${download.path}")
    private String valueUploadDownloadFiles;

    private final BookService bookService;
    private final BookRepo bookRepo;

    private final BookFilesRepo bookFilesRepo;

    private final AuthorRepo authorRepo;

    private final TagRepo tagRepo;

    private final ResourceStorage resourceStorage;


    @Autowired
    public AddAndDeleteBookService(BookService bookService, BookRepo bookRepo, BookFilesRepo bookFilesRepo, AuthorRepo authorRepo, TagRepo tagRepo, ResourceStorage resourceStorage) {
        this.bookService = bookService;
        this.bookRepo = bookRepo;
        this.bookFilesRepo = bookFilesRepo;
        this.authorRepo = authorRepo;
        this.tagRepo = tagRepo;
        this.resourceStorage = resourceStorage;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void save(BookChangeRequest request) throws BookException, IOException {
        Book book = new Book(request);
        List<Tag> allTagsBySlugs = tagRepo.findAllBySlugIn(request.getTags());
        book.setTags(allTagsBySlugs);
        List<Author> authors = authorRepo.findAllBySlugIn(request.getAuthorsIds());
        book.setAuthors(authors);
        List<BookFile> listBooksFiles = resourceStorage.createListBooksFiles(request.getBookFiles(), valueUploadDownloadFiles);
        book.setBookFiles(listBooksFiles);
        bookRepo.save(book);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteBook(String slug) throws BookException {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException("Книга не найдена");
        bookRepo.delete(bookBySlug);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void editBook(BookChangeRequest request, String slug) throws IOException, BookException {
        Book bookBySlug = bookService.getBookBySlug(slug);
        if (bookBySlug == null)
            throw new BookException("Книга не найдена");
        bookBySlug.setBestseller(request.getIsBestseller());
        bookBySlug.setPrice(request.getPrice());
        bookBySlug.setDescription(request.getDescription());
        bookBySlug.setTitle(request.getTitle());
        bookBySlug.setDiscount(request.getDiscount());
        List<BookFile> newBooksFile = new ArrayList<>(bookBySlug.getBookFiles());
        List<BookFile> listBooksFiles = resourceStorage.createListBooksFiles(request.getBookFiles(), valueUploadDownloadFiles);
        for (BookFile bookFile : listBooksFiles) {
            for (int i = 0; i < bookBySlug.getBookFiles().size(); i++) {
                BookFile bookFile1 = bookBySlug.getBookFiles().get(i);
                if (bookFile1.getTypeId().equals(bookFile.getTypeId())) {
                    newBooksFile.set(i, bookFile);
                }
            }
        }
        bookBySlug.setBookFiles(newBooksFile);
        bookFilesRepo.deleteAll(newBooksFile);
        List<Author> allBySlugIn = authorRepo.findAllBySlugIn(request.getAuthorsIds());
        bookBySlug.setAuthors(allBySlugIn);
        bookRepo.save(bookBySlug);
    }
}
