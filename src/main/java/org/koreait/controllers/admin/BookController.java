package org.koreait.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.Menu;
import org.koreait.commons.MenuDetail;
import org.koreait.entities.Book;
import org.koreait.models.books.BookInfoService;
import org.koreait.models.books.BookSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBookController")
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController implements CommonProcess {

    private String tplCommon = "admin/book/";
    private final BookSaveService saveService;
    private final BookInfoService infoService;

    private final HttpServletRequest request;

    /**
     * 도서 목록
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess(model, "list");
        return tplCommon + "index";
    }

    /**
     * 도서 등록
     * 
     */
    @GetMapping("/add")
    public String add(@ModelAttribute BookForm bookForm, Model model) {
        commonProcess(model, "add");
        return tplCommon + "add";
    }

    /**
     * 도서 수정
     *
     */
    @GetMapping("/edit/{bookNo}")
    public String edit(@PathVariable Long bookNo, Model model) {
        commonProcess(model, "edit");
        BookForm bookForm = infoService.getBookForm(bookNo);
        model.addAttribute("bookForm", bookForm);

        return tplCommon + "edit";
    }

    /**
     * 도서 등록/수정 처리
     *
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

        return tplCommon + "category";
    }

    /**
     * 도서분류 추가, 수정, 삭제 처리
     *
     */
    @PostMapping("/category")
    public String categorySave(Model model) {
        commonProcess(model, "category");

        return "common/_execute_script";
    }

    @Override
    public void commonProcess(Model model, String mode) {


        String pageTitle = "도서 목록";
        if (mode.equals("add")) {
            pageTitle = "도서 등록";
        } else if (mode.equals("edit")) {
            pageTitle = "도서 수정";
        } else if (mode.equals("category")) {
            pageTitle = "도서 분류";
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        if (mode.equals("add") || mode.equals("edit") || mode.equals("save")) {
            addCommonScript.add("ckeditor/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("book/form");
        }

        model.addAttribute("menuCode", "book");
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);

        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);

        // 서브 메뉴 조회
        List<MenuDetail> submenus = Menu.gets("book");
        model.addAttribute("submenus", submenus);
    }
}
