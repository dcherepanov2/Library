package com.example.MyBookShopApp.data.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name ="token_blacklist")
@Data
public class JwtLogoutToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
}