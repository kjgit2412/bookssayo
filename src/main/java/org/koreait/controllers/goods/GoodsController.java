package org.koreait.controllers.goods;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.entities.Book;
import org.koreait.models.books.BookInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/front/goods")
@RequiredArgsConstructor
public class GoodsController implements CommonProcess, ScriptExceptionProcess {

    private final BookInfoService infoService;
    private final Utils utils;

    @GetMapping("/view/{bookNo}")
    public String view(@PathVariable Long bookNo, Model model) {
        try {
            Book data = infoService.get(bookNo);
            commonProcess(model, "view", data.getBookNm());

            model.addAttribute("data", data);

        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertBackException(e.getMessage());
        }

        return utils.view("goods/view");
    }

    public void commonProcess(Model model, String mode) {
        commonProcess(model, mode, null);
    }

    public void commonProcess(Model model, String mode, String addTitle) {
        String pageTitle = "";
        if (mode.equals("view")) {
            if (addTitle != null && !addTitle.isBlank()) addTitle += "|";
            pageTitle = addTitle;
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if (mode.equals("view")) {
            addScript.add("goods/view");
        }

        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}
