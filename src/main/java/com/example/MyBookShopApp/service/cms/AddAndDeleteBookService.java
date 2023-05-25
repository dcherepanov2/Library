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
import com.example.MyBookShopApp.service.bookServices.ResourceStorage;
import com.example.MyBookShopApp.service.senders.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddAndDeleteBookService {

    @Value("${download.path}")
    private String valueUploadDownloadFiles;
    private final BookRepo bookRepo;

    private final BookFilesRepo bookFilesRepo;

    private final AuthorRepo authorRepo;

    private final TagRepo tagRepo;

    private final ResourceStorage resourceStorage;

    private final ValidationService validationService;

    @Autowired
    public AddAndDeleteBookService(BookRepo bookRepo, BookFilesRepo bookFilesRepo, AuthorRepo authorRepo, TagRepo tagRepo, ResourceStorage resourceStorage, ValidationService validationService) {
        this.bookRepo = bookRepo;
        this.bookFilesRepo = bookFilesRepo;
        this.authorRepo = authorRepo;
        this.tagRepo = tagRepo;
        this.resourceStorage = resourceStorage;
        this.validationService = validationService;
    }

    public void save(BookChangeRequest request) throws BookException, IOException {
        if (request.getIsBestseller() == null)
            throw new BookException("Поле бестселлер не было заполнено");
        if (request.getTitle() == null)
            throw new BookException("Поле название не было заполнено");
        if (request.getDiscount() == null)
            throw new BookException("Поле скидка не было заполнено");
        if (request.getPrice() == null)
            throw new BookException("Поле цена не было заполнено");
        if (request.getDescription() == null)
            throw new BookException("Поле описание не было заполнено");
        if (request.getBookFiles() == null)
            throw new BookException("Не указаны файлы для скачивания книги");
        if (request.getBookFiles().size() != 3)
            throw new BookException("Добавьте три файла типа: EPUB, FB2, PDF");
        if (request.getAuthorsIds() == null)
            throw new BookException("Не указаны авторы для создания книги");

        Book book = new Book(request);

        if (request.getTags() != null) {
            List<Tag> allTagsBySlugs = tagRepo.findAllBySlugIn(request.getTags());
            book.setTags(allTagsBySlugs);
        }

        if (request.getAuthorsIds() != null) {
            List<Author> authors = authorRepo.findAllBySlugIn(request.getAuthorsIds());
            book.setAuthors(authors);
        }

        List<BookFile> listBooksFiles = resourceStorage.createListBooksFiles(request.getBookFiles(), valueUploadDownloadFiles);
        book.setBookFiles(listBooksFiles);

        if (validationService.checkAllFilesAddToBook(book))
            throw new BookException("Не были добавлены все нужные файлы. Проверьте, что было добавлено 3 файла типа: EPUB, PDF, FB2");

        bookRepo.save(book);
    }

    public void deleteBook(Book bookBySlug) {
        bookRepo.delete(bookBySlug);
    }

    public void editBook(BookChangeRequest request, Book bookBySlug) throws IOException {
        if (request.getIsBestseller() != null) {
            bookBySlug.setBestseller(request.getIsBestseller());
        }
        if (request.getPrice() != null) {
            bookBySlug.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            bookBySlug.setDescription(request.getDescription());
        }
        if (request.getTitle() != null) {
            bookBySlug.setTitle(request.getTitle());
        }
        if (request.getDiscount() != null) {
            bookBySlug.setDiscount(request.getDiscount());
        }
        if (request.getBookFiles() != null && request.getBookFiles().size() <= 3) {
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
            bookFilesRepo.deleteAll(newBooksFile); // Исправлено на newBooksFile
        }

        if (request.getAuthorsIds() != null) {
            List<Author> allBySlugIn = authorRepo.findAllBySlugIn(request.getAuthorsIds());
            bookBySlug.setAuthors(allBySlugIn);
        }

        bookRepo.save(bookBySlug);
    }
}
