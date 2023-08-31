package org.koreait.controllers.front;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.ListData;
import org.koreait.commons.Menu;
import org.koreait.commons.MenuDetail;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.Book;
import org.koreait.models.books.BookInfoService;
import org.koreait.models.books.BookSearch;
import org.koreait.models.categories.CategoryInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("frontController")
@RequestMapping("/front/main")
@RequiredArgsConstructor
public class FrontController implements CommonProcess {

    private final BookInfoService infoService;
    private final CategoryInfoService categoryInfoService;
    private final HttpServletRequest request;

    @GetMapping
    public String main(Model model){
        commonProcess(model, "북싸요");

        BookSearch search = new BookSearch();
        ListData<Book> data = infoService.getList(search);

        model.addAttribute("items", data.getContent());

        return "front/main/index";
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
