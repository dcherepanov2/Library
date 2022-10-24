package com.example.MyBookShopApp.data.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.text.DecimalFormat;
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
    private String username;

    @Column(name = "reg_time")
    private LocalDate dateRegistration;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "hash")
    private String hash;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns =  @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}