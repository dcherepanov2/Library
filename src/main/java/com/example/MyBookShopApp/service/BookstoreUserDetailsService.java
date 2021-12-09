//package com.example.MyBookShopApp.service;
//
//
//import com.example.MyBookShopApp.data.user.BookstoreUserDetails;
//import com.example.MyBookShopApp.data.user.UserEntity;
//import com.example.MyBookShopApp.repo.BookStoreUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BookstoreUserDetailsService implements UserDetailsService {
//
//    private final BookStoreUserRepository bookStoreUserRepository;
//
//    @Autowired
//    public BookstoreUserDetailsService(BookStoreUserRepository bookStoreUserRepository) {
//        this.bookStoreUserRepository = bookStoreUserRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         UserEntity bookStoreUser = bookStoreUserRepository.findUserEntityByEmail(username);
//         if(bookStoreUser!=null){
//             String code = bookStoreUserRepository.getCodeUserByContact(username);
//             return new BookstoreUserDetails(bookStoreUser, code);
//         }else {
//             throw new UsernameNotFoundException("user not found");
//         }
//    }
//}
