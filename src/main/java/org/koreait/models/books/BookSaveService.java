package org.koreait.models.books;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.koreait.commons.constants.BookStatus;
import org.koreait.commons.validators.RequiredValidator;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.models.categories.CategoryInfoService;
import org.koreait.repositories.BookRepository;
import org.koreait.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSaveService implements RequiredValidator {
    private final BookRepository bookRepository;
    private final CategoryInfoService categoryInfoService;
    private final FileInfoRepository fileInfoRepository;
    private final Utils utils;

    public void save(BookForm form) {
        String gid = form.getGid();
        Long bookNo = form.getBookNo();
        Book book = null;
        if (bookNo != null) {
            book = bookRepository.findById(bookNo).orElseThrow(BookNotFoundException::new);
        } else {
            book = new Book();
            book.setGid(gid); // 그룹 ID는 처음 추가할때만 저장, 그 이후는 변경 불가
            System.out.println("bookNo1 = " + bookNo);
        }

        book.setCategory(categoryInfoService.get(form.getCateCd()));
        book.setBookNm(form.getBookNm());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());
        book.setPrice(form.getPrice());
        book.setStock(form.getStock());
        book.setDescription(form.getDescription());
        book.setStatus(BookStatus.valueOf(form.getStatus()));

        bookRepository.saveAndFlush(book);
        form.setBookNo(book.getBookNo());

        /** 파일 업로드 완료 처리 */
        fileInfoRepository.processDone(gid);
    }

    /**목록 저장 처리*/
    public void saveList(BookForm form){
        List<Integer> chks = form.getChkNo();
        nullCheck(chks, utils.getMessage("NotSelected.edit", "validation"));

        for(Integer chk : chks){
            String bookNo = utils.getParam("bookNo_" + chk);
            Long bookNoLong = Long.parseLong(bookNo);
            Book item = bookRepository.findById(bookNoLong).orElse(null);
            if (item == null) continue;

            //item.setCategory(categoryInfoService.get(utils.getParam("cateCd_" + chk)));
            item.setStatus(BookStatus.valueOf(utils.getParam("status_" + chk))); // 판매상태
            item.setStock(Integer.parseInt(utils.getParam("stock_" + chk)));
            item.setListOrder(Long.parseLong(utils.getParam("listOrder_" + chk)));

        }
        bookRepository.flush();

    }
}