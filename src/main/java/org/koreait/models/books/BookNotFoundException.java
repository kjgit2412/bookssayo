package org.koreait.models.books;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 도서 조회 실패 예외
 *
 */
public class BookNotFoundException extends CommonException {
    public BookNotFoundException() {
        super(bundleValidation.getString("NotFound.book"), HttpStatus.BAD_REQUEST);
    }
}
