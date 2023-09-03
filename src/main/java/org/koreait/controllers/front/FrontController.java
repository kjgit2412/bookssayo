package org.koreait.controllers.front;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.commons.constants.BookStatus;
import org.koreait.controllers.admin.BookForm;
import org.koreait.entities.Book;
import org.koreait.models.books.BookInfoService;
import org.koreait.models.books.BookSearch;
import org.koreait.models.categories.CategoryInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("frontController")
@RequestMapping("/front")
@RequiredArgsConstructor
public class FrontController implements CommonProcess {

    private final BookInfoService infoService;
    private final CategoryInfoService categoryInfoService;
    private final HttpServletRequest request;

    /** 메인 페이지 */
    @GetMapping("main")
    public String main(@ModelAttribute BookSearch search, Model model){
        commonProcess(model, "main");

        ListData<Book> data = infoService.getList(search);
        // 각 도서에 대한 이미지 정보를 가져와서 모델에 추가
        for (Book book : data.getContent()) {
            infoService.addFileInfo(book);
        }
        model.addAttribute("items", data.getContent());

        return "front/main/index";
    }

    /** 도서 상세 페이지*/
    @GetMapping("/goods/view/{bookNo}")
    public String view(@PathVariable Long bookNo, Model model) {
        try {
            Book data = infoService.get(bookNo);
            commonProcess(model, "view", data.getBookNm());

            model.addAttribute("data", data);

        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertBackException(e.getMessage());
        }

        return "front/goods/view";
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
        else if(mode.equals("main")){
            pageTitle = "북싸요";
        }


        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        if (mode.equals("view")) {
            addScript.add("goods/view");
            addScript.add("order/cart");
        }

        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
}
