package org.koreait.controllers.front;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.ListData;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.Book;
import org.koreait.models.books.BookInfoService;
import org.koreait.models.books.BookSearch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("frontController")
@RequestMapping("/front/main")
@RequiredArgsConstructor
public class FrontController implements CommonProcess {

    private final BookInfoService infoService;

    @GetMapping
    public String main(Model model){
        commonProcess(model, "북싸요");

        BookSearch search = new BookSearch();
        ListData<Book> data = infoService.getList(search);

        model.addAttribute("items", data.getContent());

        return "front/main/index";
    }
}
