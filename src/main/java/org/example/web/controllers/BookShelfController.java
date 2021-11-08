package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.exception.FileUploadException;
import org.example.services.BookService;
import org.example.services.MediaTypeUtils;
import org.example.web.dto.Book;
import org.example.web.dto.PlaceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    private List<Book> findList = new ArrayList<>();
    private final ServletContext servlet;
    private String error = null;
    private final String UPLOAD_PATH = "E:\\uploads_javabased";//название папки, исправлять не вижу смысла

    @Autowired
    public BookShelfController(BookService bookService, ServletContext servlet) {
        this.servlet = servlet;
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("id", new PlaceHolder());
        model.addAttribute("placeholderToRemove", new PlaceHolder());
        model.addAttribute("error", error);
        model.addAttribute("findList", findList);
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("regex", new PlaceHolder());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book, Model model) {
        findList.clear();
        if (bookService.hasAllValuesEmpty(book)) {
            logger.info("book is null ; error add book ; redirect to /books/shelf");
            model.addAttribute("error", error = "Addled book has errors, please try again");
            return "redirect:/books/shelf";
        }
        error = null;
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid PlaceHolder bookIdToRemove, BindingResult bindingResult, Model model) {
        findList.clear();
        try {//можно было использовать аннотацию @Size в дто placeholder,но так как это объект используется как стринг
            //в других методах(а плодить лишние дто в проэкте я не хочу) решил обработать ошибку try catch
            if (bindingResult.hasErrors()) {
                model.addAttribute("error", error = "Removed id has errors in placeholder, please try again");
                bookService.removeBookById(Integer.valueOf(bookIdToRemove.getString()));
                return "redirect:/books/shelf";
            }
            error = null;
        } catch (Exception e) {
            logger.info("error parsing string to integer in placeholder");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeToAuthor")//удаление по автору
    public String removeBookToAuthor(@Valid PlaceHolder author, BindingResult bindingResult, Model model) {
        findList.clear();
        error = null;
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Removed author has errors in placeholder, please try again");
            return "redirect:/books/shelf";
        }
        logger.info("remove to author start work");
        logger.info(author);
        bookService.removeBookByAuthor(author.getString());
        return "redirect:/books/shelf";
    }

    @PostMapping("/removeToTittle")//удаление по тайтлу
    public String removeBookToTitle(@Valid PlaceHolder title, BindingResult bindingResult, Model model) {
        findList.clear();
        logger.info(title.getString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Removed title has errors in placeholder, please try again");
            return "redirect:/books/shelf";
        }
        error = null;
        logger.info("remove to title start work");
        logger.info(title);
        bookService.removeBookByTitle(title.getString());
        return "redirect:/books/shelf";
    }

    @PostMapping("/findId")
    public String findId(@Valid PlaceHolder id, BindingResult bindingResult, Model model) {
        findList.clear();
        if (bindingResult.hasErrors()) {
            logger.info("validation has errors");
            model.addAttribute("error", error = "Invalid id in placeholder, please try again");
            return "redirect:/books/shelf";
        }
        error = null;
        logger.info("method find id is start working" + id.getString());
        findList = bookService.findId(Integer.parseInt(id.getString()));
        logger.info(findList);
        logger.info(bookService.findId(Integer.parseInt(id.getString())));
        return "redirect:/books/shelf";
    }

    @PostMapping("/findTitle")
    public String findTitle(@Valid PlaceHolder id, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("validation has errors");
            model.addAttribute("error", error = "Invalid title in placeholder, please try again");
            return "redirect:/books/shelf";
        }
        error = null;
        findList.clear();
        logger.info("method find id is start working" + id.getString());
        if (id.getString() == null || id.getString().equals("")) {
            logger.info("title == null");
            return "redirect:/books/shelf";//если передается пустое значение - возвращаются все книги в базе
        }
        findList = bookService.findTitle(id.getString());
        logger.info(findList);
        logger.info(bookService.findTitle(id.getString()));
        return "redirect:/books/shelf";
    }


    @PostMapping("/findAuthor")
    public String findAuthor(@Valid PlaceHolder id, BindingResult bindingResult, Model model) {
        findList.clear();
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Invalid author in placeholder, please try again");
            return "redirect:/books/shelf";
        }
        error = null;
        logger.info("method find id is start working" + id.getString());
        if (id.getString() == null || id.getString().equals("")) {
            logger.info("id == null");
            return "redirect:/books/shelf";//если передается пустое значение - возвращаются все книги в базе
        }
        findList = bookService.findAuthor(id.getString());
        logger.info(findList);
        logger.info(bookService.findAuthor(id.getString()));
        return "redirect:/books/shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        logger.info("file uploading " + file.getSize());
        if (file.getSize() == 0) {
            logger.info("error upload file");
            throw new FileUploadException("Error file upload");
        }
        error = null;
        File dir = new File(UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();//используется, для создания папки, не знаю почему IDE помечает его как игнорируемую :/
        }
        String uuid = UUID.randomUUID().toString();
        String resultFileName = uuid + "." + file.getOriginalFilename();
        file.transferTo(new File(UPLOAD_PATH + "\\" + resultFileName));
        //bookService.findId(id).get(0).setFilename(resultFileName);
        return "redirect:/books/shelf";
    }

    @RequestMapping(value = "/file/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(HttpServletResponse response, @RequestParam(value = "id") Integer id) throws IOException {
        String fileName = bookService.findId(id).get(0).getFilename();
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servlet, fileName);
        logger.info("fileName: " + fileName);
        logger.info("mediaType: " + mediaType);
        File file = new File(UPLOAD_PATH + "/" + fileName);
        // Content-Type
        // application/pdf
        response.setContentType(mediaType.getType());

        // Content-Disposition
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

        // Content-Length
        response.setContentLength((int) file.length());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
    }

    @PostMapping("/deleteAuthorRegex")
    public String deleteAuthorRegex(@Valid PlaceHolder author,BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Invalid regex author to remove or not found");
            return "redirect:/books/shelf";
        }
        bookService.removeBookByAuthorRegex(author.getString());
        error = null;
        return "redirect:/books/shelf";
    }

    @PostMapping("/deleteSizeRegex")
    public String deleteSizeRegex(@Valid PlaceHolder author,BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()){
            model.addAttribute("error", error = "Invalid size author to remove or not found");
            return "redirect:/books/shelf";
        }
        bookService.removeBookBySizeRegex(author.getString());
        error = null;
        return "redirect:/books/shelf";
    }

    @PostMapping("/deleteTitleRegex")
    public String deleteTitleRegex(@Valid PlaceHolder author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Invalid title author to remove or not found");
            return "redirect:/books/shelf";
        }
        bookService.removeBookByTitleRegex(author.getString());
        error = null;
        return "redirect:/books/shelf";
    }

    @PostMapping("/findAuthorRegex")
    public String findAuthorRegex(@Valid PlaceHolder author, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", error = "Invalid regex author to find or not found");
            return "redirect:/books/shelf";
        }
        bookService.removeBookByTitleRegex(author.getString());
        error = null;
        return "redirect:/books/shelf";
    }

    @PostMapping("/findTitleRegex")
    public String findTitleRegex(@Valid PlaceHolder author, Model model) {
        if (bookService.findBookByTitleRegex(author.getString()).size() == 0) {
            model.addAttribute("error", error = "Invalid regex title to find or not found ");
            return "redirect:/books/shelf";
        }
        error = null;
        return "redirect:/books/shelf";
    }

    @PostMapping("/findSizeRegex")
    public String findSizeRegex(Integer size, Model model) {
        if (bookService.findBookBySizeRegex(size).size() == 0) {
            model.addAttribute("error", error = "Invalid regex size to find or not found ");
            return "redirect:/books/shelf";
        }
        error = null;
        return "redirect:/books/shelf";
    }
}
