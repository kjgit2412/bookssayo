package org.koreait.controllers.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class BookForm {

    private String mode;

    private Long bookNo;

    private String category;

    @NotBlank
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String bookNm;

    private int price;
    private int stock;

    private String status;

    private String description;

    private long listOrder;
}
