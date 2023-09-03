package org.koreait.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.Book;
import org.koreait.entities.Category;
import org.koreait.models.books.BookDeleteService;
import org.koreait.models.books.BookInfoService;
import org.koreait.models.books.BookSaveService;
import org.koreait.models.books.BookSearch;
import org.koreait.models.categories.CategoryDeleteService;
import org.koreait.models.categories.CategoryInfoService;
import org.koreait.models.categories.CategorySaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBookController")
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController implements CommonProcess, ScriptExceptionProcess {

    private String tplCommon = "admin/book/";
    private final BookSaveService saveService;
    private final BookInfoService infoService;
    private final BookDeleteService bookDeleteService;

    private final CategoryInfoService categoryInfoService;
    private final CategorySaveService categorySaveService;
    private final CategoryDeleteService categoryDeleteService;

    private final HttpServletRequest request;

    /**
     * 도서 목록 페이지를 불러온다.
     * @param model Spring MVC의 Model 객체로 뷰에 데이터 전달
     * @return 도서 목록 페이지의 뷰 이름
     */
    @GetMapping
    public String index(@ModelAttribute BookSearch search, Model model) {
        commonProcess(model, "list");
        search.setLimit(10);
        ListData<Book> data = infoService.getList(search);
        // 각 도서에 대한 이미지 정보를 가져와서 모델에 추가
        for (Book book : data.getContent()) {
            infoService.addFileInfo(book);
        }
        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return tplCommon + "index";
    }

    @PostMapping
    public String indexPs(BookForm form ,Model model) {
        commonProcess(model, "list");

        String mode = form.getMode();
        try {
            if (mode.equals("delete")) { // 삭제
                bookDeleteService.deleteList(form);
            }
            else if(mode.equals("edit")) {
                saveService.saveList(form);
            }
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertException(e.getMessage()); // 자바스크립트 alert 형태로 에러 출력
        }

        String script = "parent.location.reload();";
        model.addAttribute("script", script);
        return "common/_execute_script";
    }
    
    /**
     * 도서 추가 페이지를 불러온다.
     * @param bookForm 새 도서 정보를 담은 BookForm 객체
     * @param model Spring MVC의 Model 객체로 뷰에 데이터 전달
     * @return 도서 추가 페이지의 뷰 이름
     */
    @GetMapping("/add")
    public String add(@ModelAttribute BookForm bookForm, Model model) {
        commonProcess(model, "add");
        return tplCommon + "add";
    }

    /**
     * 도서 수정 페이지를 불러온다.
     * @param bookNo 수정할 도서 번호
     * @param model Spring MVC의 Model 객체로 뷰에 데이터 전달
     * @return 도서 수정 페이지의 뷰 이름
     */
    @GetMapping("/edit/{bookNo}")
    public String edit(@PathVariable Long bookNo, Model model) {
        commonProcess(model, "edit");
        BookForm bookForm = infoService.getBookForm(bookNo);
        model.addAttribute("bookForm", bookForm);

        return tplCommon + "edit";
    }

    /**
     * 도서 정보를 저장한다.
     * @param bookForm 도서 정보를 담은 BookForm 객체
     * @param errors 유효성 검사 결과를 담은 Errors 객체
     * @param model Spring MVC의 Model 객체로 뷰에 데이터 전달
     * @return 도서 목록 페이지로의 리다이렉션 또는 수정 페이지 또는 추가 페이지
     */
    @PostMapping("/save")
    public String bookSave(@Valid BookForm bookForm, Errors errors, Model model) {
        commonProcess(model, "save");

        String mode = bookForm.getMode();
        if (errors.hasErrors()) {
            return mode != null && mode.equals("edit") ? tplCommon + "edit" : tplCommon + "add";
        }

        saveService.save(bookForm);

        return "redirect:/admin/book";
    }

    /**
     * 도서분류 목록
     */
    @GetMapping("/category")
    public String category(Model model) {
        commonProcess(model, "category");
        List<Category> items = categoryInfoService.getListAll();
        model.addAttribute("items", items);

        return tplCommon + "category";
    }

    /**
     * 도서분류 추가, 수정, 삭제 처리
     */
    @PostMapping("/category")
    public String categorySave(CategoryForm form, Model model) {
        commonProcess(model, "category"); // 페이지 제목

        String mode = form.getMode();
        mode = mode == null || mode.isBlank() ? "add" : mode;
        try {
            if (mode.equals("add")) { // 등록
                categorySaveService.save(form);

            } else if (mode.equals("edit")) { // 수정
                categorySaveService.saveList(form);

            } else if (mode.equals("delete")) { // 삭제
                categoryDeleteService.deleteList(form);
            }
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertException(e.getMessage()); // 자바스크립트 alert 형태로 에러 출력
        }

        String script = "parent.location.reload();";
        model.addAttribute("script", script);
        return "common/_execute_script";

    }

    @Override
    public void commonProcess(Model model, String mode) {
        // 페이지 제목 설정을 위한 변수 초기화
        String pageTitle = "도서 목록";

        // mode 값에 따라 페이지 제목 설정
        if (mode.equals("add")) {
            pageTitle = "도서 등록";
        } else if (mode.equals("edit")) {
            pageTitle = "도서 수정";
        } else if (mode.equals("category")) {
            pageTitle = "도서 분류";
        }

        // CommonProcess의 기본 commonProcess 메서드 호출 및 페이지 제목 전달
        CommonProcess.super.commonProcess(model, pageTitle);

        // 페이지 스크립트 추가를 위한 리스트 초기화
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        // mode 값에 따라 필요한 스크립트 추가
        if (mode.equals("add") || mode.equals("edit") || mode.equals("save")) {
            addCommonScript.add("ckeditor/ckeditor"); // CKEditor 스크립트 추가
            addCommonScript.add("fileManager"); // 파일 관리자 스크립트 추가
            addScript.add("book/form"); // 도서 관련 폼 스크립트 추가
            model.addAttribute("categories", categoryInfoService.getListAll());
        } else if (mode.equals("list")) {
            model.addAttribute("categories", categoryInfoService.getListAll());
            model.addAttribute("statusList", BookStatus.getList());

        }

        // 모델에 데이터 추가
        model.addAttribute("menuCode", "book"); // 메뉴 코드 추가
        model.addAttribute("addCommonScript", addCommonScript); // 공통 스크립트 추가
        model.addAttribute("addScript", addScript); // 페이지 스크립트 추가

        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);

        // 서브 메뉴 조회
        List<MenuDetail> submenus = Menu.gets("book");
        model.addAttribute("submenus", submenus);
    }
}
