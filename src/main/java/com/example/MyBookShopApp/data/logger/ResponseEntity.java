package com.example.MyBookShopApp.data.logger;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="response")
@Getter
@NoArgsConstructor
@Entity
public class ResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "session", referencedColumnName = "id")
    private SessionEntity sessionEntity;

    @Column(name = "status_code")
    private Integer statusCode;
    @Column(name = "body")
    private String body;

    public ResponseEntity(String body, SessionEntity sessionEntity, Integer statusCode) {
        this.sessionEntity = sessionEntity;
        this.body = body;
        this.statusCode = statusCode;
    }
}
