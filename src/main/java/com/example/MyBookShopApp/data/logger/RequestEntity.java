package com.example.MyBookShopApp.data.logger;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="request")
@Getter
@NoArgsConstructor
@Entity
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "session", referencedColumnName = "id")
    private SessionEntity sessionEntity;
    @Column(name = "body")
    private String body;

    public RequestEntity(String body, SessionEntity sessionEntity) {
        this.sessionEntity = sessionEntity;
        this.body = body;
    }
}
