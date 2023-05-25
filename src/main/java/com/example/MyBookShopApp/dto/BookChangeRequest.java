package com.example.MyBookShopApp.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BookChangeRequest implements AopDto{

    private Boolean isBestseller;

    private String title;

    private String description;

    private Integer price;

    private Short discount = 0;

    private List<String> authorsIds;

    List<String> tags;

    List<MultipartFile> bookFiles;
}
