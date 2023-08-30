package org.koreait.controllers.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.FileInfo;

import java.util.List;
import java.util.UUID;

/**
 * 도서 정보를 담는 폼 클래스
 */
@Data
public class BookForm {

    private String mode; // 폼의 작업 모드 (추가 또는 수정)

    private Long bookNo; // 도서 번호 (수정 시에만 사용)

    private String cateCd; // 도서 분류 코드

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 고유 식별자, 기본값으로 UUID 사용

    @NotBlank
    private String bookNm; // 도서명

    @NotBlank
    private String author; // 저자

    @NotBlank
    private String publisher; // 출판사

    private int price; // 가격
    private int stock; // 재고

    private String status = BookStatus.READY.name(); // 도서 상태, 기본값은 READY로 설정

    private String description; // 상세 설명

    private long listOrder; // 상품 진열 가중치

    private List<FileInfo> mainImages; // 상품 메인 이미지

    private List<FileInfo> listImages; // 목록 이미지

    private List<FileInfo> editorImages; // 에디터 이미지

    private List<Integer> chkNo;
}
