package com.example.MyBookShopApp.controllers.booksController;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.review.BookReview;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.enums.BookStatus;
import com.example.MyBookShopApp.exception.BookException;
import com.example.MyBookShopApp.exception.BookWasBoughtException;
import com.example.MyBookShopApp.exception.UserInsufficientBalance;
import com.example.MyBookShopApp.repo.userrepos.RoleRepository;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import com.example.MyBookShopApp.service.authorServices.AuthorService;
import com.example.MyBookShopApp.service.bookServices.*;
import com.example.MyBookShopApp.service.payment.TransactionService;
import com.example.MyBookShopApp.service.userServices.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;

    private final RoleRepository roleRepository;
    private final AuthorService authorService;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;

    private final UserServiceImpl userService;
    private final BooksChangeStatusService booksChangeStatusService;

    @Qualifier("transactionServiceImpl")
    private final TransactionService transactionService;
    private final Book2UserService book2UserService;

    @Autowired
    public BooksController(BookService bookService, RoleRepository roleRepository, AuthorService authorService, ResourceStorage storage, BookReviewService bookReviewService, UserServiceImpl userService, BooksChangeStatusService booksChangeStatusService, TransactionService transactionService, Book2UserService book2UserService) {
        this.bookService = bookService;
        this.roleRepository = roleRepository;
        this.authorService = authorService;
        this.storage = storage;
        this.bookReviewService = bookReviewService;
        this.userService = userService;
        this.booksChangeStatusService = booksChangeStatusService;
        this.transactionService = transactionService;
        this.book2UserService = book2UserService;
    }

    @ModelAttribute("comment")
    public CommentDtoInput commentModel() {
        return new CommentDtoInput();
    }

    @ModelAttribute("downloadButton")
    public Boolean downloadButton(JwtUser user, HttpServletRequest httpServletRequest) {
        String requestURI = httpServletRequest.getRequestURI();
        final Book book = bookService.getBookBySlug(requestURI.substring(7));
        if (user != null && book != null) {
            User byHash = userService.findByHash(user.getHash());
            return byHash != null && byHash.getBook2UserEntities()
                    .stream()
                    .anyMatch(x -> x.getBookId().equals(book.getId()));
        }
        return false;
    }

    @ModelAttribute("upload")
    public Boolean uploadButton(JwtUser user) {
        if (user != null) {
            User byHash = userService.findByHash(user.getHash());
            final Role role = roleRepository.findByName("ROLE_ADMIN");
            return byHash.getRoles().stream().anyMatch(x -> x.getName().equals(role.getName()));
        }
        return false;
    }


    @GetMapping("/recent")
    public String getFilterBooksRecent(Model model) {
        LocalDateTime ldt =
                LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).minusMonths(1);
        Date from = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        model.addAttribute("bookByFilterDatePublic", new RecommendedBooksDto(bookService.getFilterBooksByDate(from, to, 0, 20)));
        return "books/recent";
    }


    @GetMapping("/popular")
    public String getBooksPopular(Model model,
                                  @CookieValue(name = "cartContents", required = false) String cartContents,
                                  @CookieValue(name = "keptContents", required = false) String keptContents,
                                  JwtUser jwtUser) {
        List<Book2UserEntity> allBookUser = book2UserService.getAllBook2User(jwtUser);
        List<Book> booksPopular = bookService.getPopularBooksData(0, 20);
        if (cartContents != null || keptContents != null || allBookUser != null)
            model.addAttribute("bookMostPopular", new RecommendedBooksDto(booksPopular, keptContents, cartContents, allBookUser));
        else
            model.addAttribute("bookMostPopular", new RecommendedBooksDto(booksPopular));
        return "books/popular";
    }

    @GetMapping("/author/{slug}")
    public String getAuthorBooks(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("bookBySelect", authorService.findBooksByAuthor(slug));
        model.addAttribute("bookBySelectForAuthorPage",
                new RecommendedBooksDto(authorService.findBooksByAuthor(slug)));
        return "/books/author";
    }

    //TODO: написать отдельный контроллер books/slug, в котором будет содержаться вся логика здесь,
    // но разбитая по ModelAttribute
    @GetMapping("/{slug}")
    @SneakyThrows
    public String getBook(@PathVariable("slug") String slug, Model model, JwtUser user) {
        Book book = bookService.getBookBySlug(slug);
        if (book == null)
            throw new BookException("Книга не найдена.");
        LinkedList<Tag> tags = new LinkedList<>(book.getTags());
        model.addAttribute("tagsBook", tags);
        model.addAttribute("lastTag", tags.get(tags.size() - 1));
        model.addAttribute("slugBook", new BookSlugDto(book, book.getAuthors()));
        List<BookReview> bookReviews = bookReviewService.reviewEntitiesBySlugBook(slug);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (BookReview bookReview : bookReviews) { //перебор всех комментариев книги
            CommentDto commentDtoLocal = new CommentDto();
            List<BookReviewLikeEntity> reviewLikeEntities = bookReviewService
                    .getLikeOrDislikeCommentBySlug(bookReview.getId());
            for (BookReviewLikeEntity bookReviewLikeEntity : reviewLikeEntities) { //перебор всех лайков одного комментария
                short localDislike = bookReviewLikeEntity.getValue();
                short localLike = bookReviewLikeEntity.getValue();
                switch (bookReviewLikeEntity.getValue()) {
                    case 1:
                        commentDtoLocal.setLike(commentDtoLocal.getLike() + localLike);
                        break;
                    case -1:
                        commentDtoLocal.setDislike(commentDtoLocal.getDislike() + Math.abs(localDislike));
                        break;
                }
            }
            if (bookReview.getText().length() > 345) {
                commentDtoLocal.setDescription(bookReview.getText().substring(0, 345));
                commentDtoLocal.setDescriptionPartTwo(bookReview.getText().substring(345));
            } else {
                commentDtoLocal.setDescription(bookReview.getText());
            }
            commentDtoLocal.setSlug(bookReview.getId());
            commentDtoLocal.setPubDate(LocalDateTime.now());
            commentDtoLocal.setUsername(bookReview.getUserId().getUsername());
            commentDtos.add(commentDtoLocal);
        }
        Map<Integer, Integer> ratingTable = bookService.getBookRateTableBySlug(slug);
        int sum = ratingTable.get(1) + ratingTable.get(2) + ratingTable.get(3) + ratingTable.get(4) + ratingTable.get(5);
        model.addAttribute("ratingTableSum", sum);
        model.addAttribute("ratingMedium", bookReviewService.getRatingMedium(ratingTable));
        model.addAttribute("comments", commentDtos);
        model.addAttribute("ratingTable", bookService.getBookRateTableBySlug(slug));
        model.addAttribute("unAuthUserName", "Анонимный анонимус");
        if (user != null)
            model.addAttribute("unAuthUserName", user.getUsername());
        return "/books/slug";
    }

    @PostMapping("/changeBookStatus/{slug}")
    @SneakyThrows
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(name = "keptContents", required = false) String keptContents,
                                         HttpServletResponse response,
                                         Model model,
                                         @RequestBody BookChangeStatusDto bookChangeStatusDto,
                                         JwtUser jwtUser) {
        if (bookChangeStatusDto.getStatus().equals(BookStatus.CART)) {
            booksChangeStatusService.addCookieToCart(keptContents, cartContents, slug, response, model);
        } else if (bookChangeStatusDto.getStatus().equals(BookStatus.UNLINK)) {
            booksChangeStatusService.deleteSlugCookie(keptContents, cartContents, slug, response, model);
        } else if (bookChangeStatusDto.getStatus().equals(BookStatus.KEPT)) {
            booksChangeStatusService.addCookieToKept(keptContents, cartContents, slug, response, model);
        } else if (bookChangeStatusDto.getStatus().equals(BookStatus.ARCHIVED)) {
            booksChangeStatusService.addBookToArchive(keptContents, cartContents, response, jwtUser, slug);
        }
        return "redirect:/books/" + slug;
    }

    @GetMapping("/recommended")
    @ResponseBody
    public RecommendedBooksDto getBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit,
                                            @CookieValue(name = "cartContents", required = false) String cartContents,
                                            @CookieValue(name = "keptContents", required = false) String keptContents,
                                            JwtUser jwtUser) {
        List<Book2UserEntity> allBookUser = book2UserService.getAllBook2User(jwtUser);
        if (cartContents != null || keptContents != null || allBookUser != null)
            return new RecommendedBooksDto(bookService.getRecommendedBooks(offset, limit, jwtUser), keptContents, cartContents, allBookUser);
        else
            return new RecommendedBooksDto(bookService.getRecommendedBooks(offset, limit, jwtUser));
    }


    @PostMapping("/{slug}/img/save")
    @Consumes({javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA})
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        storage.saveNewBookImage(file, slug);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/pay")
    @SneakyThrows
    public String payBook(JwtUser jwtUser, @CookieValue("cartContents") String cart, HttpServletResponse httpServletResponse) {
        List<String> strings;
        if (cart.contains("/"))
            strings = Arrays.asList(cart.split("/"));
        else
            strings = Collections.singletonList(cart);
        List<Book> booksCart = bookService.getBooksBySlugs(strings);
        Integer sum = booksCart.stream().map(Book::getPrice).reduce(Integer::sum).orElse(0);
        User user = userService.findByHash(jwtUser.getHash());
        if (user != null && user.getBalance() - sum > 0) {
            for (Book book : booksCart) {
                if (user.getBook2UserEntities().stream().anyMatch(x -> x.getBookId().equals(book.getId())))
                    throw new BookWasBoughtException("Книга была куплена ранее.");
            }
            user.setBalance(user.getBalance() - sum);
            userService.saveUser(user);
            List<BalanceTransactionEntity> transactions = booksCart.stream()
                    .map(x -> new BalanceTransactionEntity(jwtUser.getId(), LocalDateTime.now(), Double.valueOf(x.getPrice()), x.getId(), ""))
                    .collect(Collectors.toList());
            List<Book2UserEntity> book2user = booksCart.stream()
                    .map(x -> new Book2UserEntity(LocalDateTime.now(), 1, x.getId(), Math.toIntExact(jwtUser.getId())))
                    .collect(Collectors.toList());
            transactionService.saveAll(transactions);
            bookService.saveAllBook2User(book2user);
            Cookie cookie = new Cookie("cartContents", "");
            httpServletResponse.addCookie(cookie);
            return "redirect:/my";
        }
        throw new UserInsufficientBalance("Недостаточно средств для покупки.");
    }
}
