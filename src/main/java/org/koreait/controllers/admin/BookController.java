package org.koreait.controllers.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
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

    /**
     * 상품 목록(도서 목록)
     *
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess(model, "list");
        return tplCommon + "index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute BookForm bookForm, Model model) {
        commonProcess(model, "add");
        return tplCommon + "add";
    }

    @GetMapping("/edit/{bookNo}")
    public String edit(@PathVariable Long bookNo, Model model) {
        commonProcess(model, "edit");
        return tplCommon + "edit";
    }

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

    @Override
    public void commonProcess(Model model, String mode) {


        String pageTitle = "도서 목록";
        if (mode.equals("add")) {
            pageTitle = "도서 등록";
        } else if (mode.equals("edit")) {
            pageTitle = "도서 수정";
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
    }
}
