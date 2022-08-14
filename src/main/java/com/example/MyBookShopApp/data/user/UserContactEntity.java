package com.example.MyBookShopApp.data.user;



import com.example.MyBookShopApp.data.enums.ContactType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_contact")
public class UserContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//
//    @Column(columnDefinition = "INT NOT NULL")
//    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    private User userId;

    @Column(columnDefinition = "TINYINT NOT NULL")
    private ContactType type;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private short approved;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String code;

    @Column(name = "code_trials",columnDefinition = "INT")
    private int codeTrails;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime codeTime;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public short getApproved() {
        return approved;
    }

    public void setApproved(short approved) {
        this.approved = approved;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCodeTrails() {
        return codeTrails;
    }

    public void setCodeTrails(int codeTrails) {
        this.codeTrails = codeTrails;
    }

    public LocalDateTime getCodeTime() {
        return codeTime;
    }

    public void setCodeTime(LocalDateTime codeTime) {
        this.codeTime = codeTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}