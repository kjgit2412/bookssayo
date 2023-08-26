package org.koreait.controllers.admin;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.FileInfo;

import java.util.List;
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

    private String status = BookStatus.READY.name();

    private String description;

    private long listOrder;

    private List<FileInfo> mainImages; // 상품 메인 이미지

    private List<FileInfo> listImages; // 목록 이미지

    private List<FileInfo> editorImages; // 에디터 이미지
}
