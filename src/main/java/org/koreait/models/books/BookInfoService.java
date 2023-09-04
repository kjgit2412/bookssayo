package org.koreait.models.books;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ListData;
import org.koreait.commons.Pagination;
import org.koreait.commons.constants.BookStatus;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.entities.Category;
import org.koreait.entities.FileInfo;
import org.koreait.entities.QBook;
import org.koreait.models.files.FileInfoService;
import org.koreait.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 도서 정보 조회 서비스
 *
 */
@Service
@RequiredArgsConstructor
public class BookInfoService {
    private final EntityManager em;
    private final BookRepository bookRepository;
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;

    /**
     * 도서 개별 조회
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
        Category category = book.getCategory();
        BookForm form = new ModelMapper().map(book, BookForm.class);
        form.setStatus(book.getStatus().name());
        form.setCateCd(category == null ? null : category.getCateCd());
        return form;
    }

    /**
     * 도서 목록 조회
     *
     * @param search
     * @return
     */
    public ListData<Book> getList(BookSearch search) {
        QBook book = QBook.book;
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int page = search.getPage();
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * limit;

        /** 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();

        String cateCd = search.getCateCd();
        List<String> cateCds = search.getCateCds();
        BookStatus status = search.getStatus();
        List<BookStatus> statuses = search.getStatuses();
        Long bookNo = search.getBookNo();
        String sopt = search.getSopt();
        String skey = search.getSkey();

        /** 도서 분류 검색 처리 S */
        if (cateCd != null && !cateCd.isBlank()) {
            andBuilder.and(book.category.cateCd.eq(cateCd));
        }

        if (cateCds != null && cateCds.size() > 0) {
            andBuilder.and(book.category.cateCd.in(cateCds));
        }
        /** 도서 분류 검색 처리 E */

        /** 판매 상태 검색 처리 S */
        if (status != null) {
            andBuilder.and(book.status.eq(status));
        }

        if (statuses != null && statuses.size() > 0) {
            andBuilder.and(book.status.in(statuses));
        }
        /** 판매 상태 검색 처리 E */

        /** 도서 번호 */
        if (bookNo != null) {
            andBuilder.and(book.bookNo.eq(bookNo));
        }

        /** 조건 및 키워드 검색 S */
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("all")) { // 통합 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(book.bookNo.stringValue().contains(skey))
                        .or(book.bookNm.containsIgnoreCase(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("bookNm")) {
                andBuilder.and(book.bookNm.containsIgnoreCase(skey));
            } else if (sopt.equals("bookNo")) {
                andBuilder.and(book.bookNo.stringValue().contains(skey));
            }
        }
        /** 조건 및 키워드 검색 E */

        /** 검색 처리 E */

        /** 정렬 처리 S */
        // listOrder_DESC,createdAt_ASC
        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        String sort = search.getSort();
        if (sort != null && !sort.isBlank()) {
            List<String[]> sorts = Arrays.stream(sort.trim().split(","))
                    .map(s -> s.split("_")).toList();
            PathBuilder pathBuilder = new PathBuilder(Book.class, "book");

            for (String[] _sort : sorts) {
                Order direction = Order.valueOf(_sort[1].toUpperCase()); // 정렬 방향
                orderSpecifier.add(new OrderSpecifier(direction, pathBuilder.get(_sort[0])));
            }
        }
        /** 정렬 처리 E */

        JPAQueryFactory factory = new JPAQueryFactory(em);
        List<Book> items = factory.selectFrom(book)
                .leftJoin(book.category)
                .fetchJoin()
                .limit(limit)
                .offset(offset)
                .where(andBuilder)
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .fetch();

        ListData<Book> data = new ListData<>();
        data.setContent(items);



        /* Todo : 페이징 처리 로직 추가 */
        int total = (int)bookRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, total, 10, limit, request);
        data.setPagination(pagination);

        return data;
    }

    /**
     * 첨부된 이미지 추가 처리
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
