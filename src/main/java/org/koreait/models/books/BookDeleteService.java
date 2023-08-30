package org.koreait.models.books;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.koreait.commons.validators.RequiredValidator;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookDeleteService implements RequiredValidator {
    private final BookRepository repository;
    private final Utils utils;

    /**
     * 도서 삭제
     * @param bookNo
     */
    public void delete(Long bookNo) {
        Book item = repository.findById(bookNo).orElse(null);
        if (item != null) {
            repository.delete(item);
            repository.flush();
        }
    }

    /**
     *  목록 삭제 처리
     */
    public void deleteList(BookForm form) {
        List<Book> items = new ArrayList<>();
        List<Integer> checks = form.getChkNo();
        nullCheck(checks, utils.getMessage("NotSelected.delete", "validation"));

        for (Integer chk : checks) {
            String bookNo = utils.getParam("bookNo_" + chk);
            Long bookNoLong = Long.parseLong(bookNo);
            Book item = repository.findById(bookNoLong).orElse(null);
            if (item == null) continue;

            items.add(item);
        }
        repository.deleteAll(items);
        repository.flush();
    }
}
