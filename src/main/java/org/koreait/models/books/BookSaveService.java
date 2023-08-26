package org.koreait.models.books;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.repositories.BookRepository;
import org.koreait.repositories.FileInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSaveService {
    private final BookRepository bookRepository;
    private final FileInfoRepository fileInfoRepository;

    public void save(BookForm form) {
        String gid = form.getGid();
        Long bookNo = form.getBookNo();
        Book book = null;
        if (bookNo != null) {
            book = bookRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        } else {
            book = new ModelMapper().map(form, Book.class);
        }


        /** 파일 업로드 완료 처리 */
        fileInfoRepository.processDone(gid);
    }
}
