package org.example.web.dto;

import com.sun.istack.internal.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class Book {

    private Integer id;
    @NotEmpty()
    private String author;
    @NotEmpty()
    private String title;
    @NotEmpty()
    @Size(min = 1,max=1000000)
    private Integer size;

    private String filename;
}
