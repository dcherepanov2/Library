package com.example.MyBookShopApp.data.logger;

import lombok.Data;

import javax.persistence.*;

@Table(name="throw-log")
@Entity
@Data
public class ThrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "session", referencedColumnName = "id")
    private SessionEntity sessionEntity;

    @Column(name="message")
    private String message;

    public ThrowEntity(SessionEntity sessionEntity, String message) {
        this.sessionEntity = sessionEntity;
        this.message = message;
    }

    public ThrowEntity() {
    }
}
