package org.koreait.controllers.member;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.Menu;
import org.koreait.commons.MenuDetail;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.constants.BookStatus;
import org.koreait.entities.Buyer;
import org.koreait.entities.Member;
import org.koreait.models.buyer.BuyerListService;
import org.koreait.models.member.MemberInfo;
import org.koreait.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("AdminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController implements CommonProcess, ScriptExceptionProcess {


    private String tplCommon = "admin/member/";

    private final HttpServletRequest request;


    //회원 관리 메인
    @GetMapping
    public String index(Model model) {
        commonProcess(model, "list");
        return tplCommon + "index";
    }



    @Override
    public void commonProcess(Model model, String mode) {
        String pageTitle = "회원 목록";
        if (mode.equals("view")) {
            pageTitle = "회원 상세";
        } else if (mode.equals("category")) {
            pageTitle = "회원 분류";
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        model.addAttribute("menuCode", "member");
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);

        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);

        // 서브 메뉴 조회
        List<MenuDetail> submenus = Menu.gets("member");
        model.addAttribute("submenus", submenus);
    }

}