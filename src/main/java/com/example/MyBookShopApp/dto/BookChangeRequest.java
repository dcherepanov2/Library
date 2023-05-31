package com.example.MyBookShopApp.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class BookChangeRequest implements AopDto{

    @NotNull(message = "isBestseller cannot be null")
    private Boolean isBestseller;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @Size(max = 1000, message = "description must be up to 1000 characters")
    private String description;

    @Positive(message = "price must be a positive value")
    private Integer price;

    @Min(value = 0, message = "discount cannot be negative")
    @Max(value = 100, message = "discount cannot be greater than 100")
    private Short discount = 0;

    @NotEmpty(message = "authorsIds cannot be empty")
    private List<String> authorsIds;

    @NotEmpty(message = "tags cannot be empty")
    private List<String> tags;

    @NotEmpty
    @Size(min = 3, max = 3)
    List<MultipartFile> bookFiles;
}
