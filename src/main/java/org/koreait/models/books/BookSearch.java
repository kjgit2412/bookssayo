package org.koreait.models.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.BookStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BookSearch {
    private int page = 1; // 페이지 번호
    private int limit = 20; // 1페이지당 레코드 갯수

    private String cateCd; // 분류 코드
    private List<String> cateCds; // 분류코드 목록
    private List<BookStatus> statuses;
    private BookStatus status;
    private Long bookNo; // 도서 번호
    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

    private String sort; // 정렬
}
