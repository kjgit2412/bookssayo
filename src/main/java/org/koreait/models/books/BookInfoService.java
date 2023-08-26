package org.koreait.models.books;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.entities.FileInfo;
import org.koreait.models.files.FileInfoService;
import org.koreait.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 도서 정보 조회 서비스
 *
 */
@Service
@RequiredArgsConstructor
public class BookInfoService {
    private final BookRepository bookRepository;
    private final FileInfoService fileInfoService;

    /**
     * 도서 개별 조회
     *
     * @param bookNo
     * @return
     */
    public Book get(Long bookNo) {
        Book book = bookRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        addFileInfo(book);

        return book;
    }

    /**
     * 도서 엔티티를 도서 양식으로 반환
     * 
     * @param bookNo
     * @return
     */
    public BookForm getBookForm(Long bookNo) {
        Book book = get(bookNo);
        BookForm form = new ModelMapper().map(book, BookForm.class);
        form.setStatus(book.getStatus().name());

        return form;
    }

    /**
     * 첨부된 이미지 추가 처리
     *
     * @param book
     */
    public void addFileInfo(Book book) {
        String gid = book.getGid();
        List<FileInfo> mainImages = fileInfoService.getListDone(gid, "main");
        List<FileInfo> listImages = fileInfoService.getListDone(gid, "list");
        List<FileInfo> editorImages = fileInfoService.getListDone(gid, "editor");
        book.setMainImages(mainImages);
        book.setListImages(listImages);
        book.setEditorImages(editorImages);
    }
}
