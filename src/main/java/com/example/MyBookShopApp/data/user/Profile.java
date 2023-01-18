package com.example.MyBookShopApp.data.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;
}
