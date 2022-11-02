package com.example.MyBookShopApp.service.builders;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.data.user.Role;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.repo.authorrepos.AuthorRepo;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.tagrepos.TagRepo;
import com.example.MyBookShopApp.repo.userrepos.RoleRepository;
import com.example.MyBookShopApp.repo.userrepos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class SaveObjectUtil {
    private final RoleRepository roleRepository;

    private final UserRepo userRepo;

    private final BookRepo bookRepo;

    private final TagRepo tagRepo;

    private final AuthorRepo authorRepo;

    @Autowired
    public SaveObjectUtil(RoleRepository roleRepository, UserRepo userRepo, BookRepo bookRepo, TagRepo tagRepo, AuthorRepo authorRepo) {
        this.roleRepository = roleRepository;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.tagRepo = tagRepo;
        this.authorRepo = authorRepo;
    }

    public void saveBook(Book book){
        bookRepo.save(book);
    }
    public void saveRole(Role role){
        roleRepository.save(role);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public void saveAuthor(Author author) {
        authorRepo.save(author);
    }

    public void saveTag(Tag tag) {
        tagRepo.save(tag);
    }
}
