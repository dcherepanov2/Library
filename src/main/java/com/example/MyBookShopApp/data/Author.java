package com.example.MyBookShopApp.data;

import javax.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String first_name;

    private String last_name;

    private String biographyPreview;

    private String biographySecondPart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char firstLetter(){
        return last_name.charAt(0);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBiographySecondPart() {
        return biographySecondPart;
    }

    public void setBiographySecondPart(String biographySecondPart) {
        this.biographySecondPart = biographySecondPart;
    }

    public String getBiographyPreview() {
        return biographyPreview;
    }

    public void setBiographyPreview(String biographyPreview) {
        this.biographyPreview = biographyPreview;
    }

}
