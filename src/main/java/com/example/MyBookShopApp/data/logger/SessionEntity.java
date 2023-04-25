package com.example.MyBookShopApp.data.logger;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name ="session")
@Getter
@NoArgsConstructor
@Entity
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "url")
    private String url;

    public SessionEntity(String url) {
        this.url = url;
    }
}
