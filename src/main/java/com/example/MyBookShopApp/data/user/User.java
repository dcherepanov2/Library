package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User{
    public User(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String username;

    @Column(name = "reg_time")
    @NotNull
    private LocalDate dateRegistration;

    @Column(name = "balance")
    @NotNull
    private Double balance;

    @Column(name = "hash")
    @NotNull
    private String hash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotNull
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book2user"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Book2UserEntity> book2UserEntities;
}
