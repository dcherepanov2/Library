package org.example.web.dto;


import javax.validation.constraints.NotEmpty;

//@Data
//@NoArgsConstructor
//@ToString
public class PlaceHolder {
    //@Getter
    //@Setter
    @NotEmpty
    String string;

    public PlaceHolder() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
