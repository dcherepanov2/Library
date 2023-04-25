package com.example.MyBookShopApp.data.book.review;

import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.ContactMessageDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class MessageEntity {
    public MessageEntity() {
    }

    public MessageEntity(ContactMessageDto contactMessageDto, User user) {
        this.time = LocalDateTime.now();
        this.userId = user;
        this.email = contactMessageDto.getEmail();
        this.name = contactMessageDto.getName();
        this.subject = contactMessageDto.getTheme();
        this.text = contactMessageDto.getMessage();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @JoinColumn(name = "user_id", columnDefinition = "INT")
    @OneToOne
    private User userId;

    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "subject", columnDefinition = "VARCHAR(255) NOT NULL")
    private String subject;

    @Column(name = "text", columnDefinition = "TEXT NOT NULL")
    private String text;

}
