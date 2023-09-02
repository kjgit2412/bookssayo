package org.koreait.controllers.member;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.entities.Member;
import org.koreait.models.member.MemberListService;
import org.koreait.models.member.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("AdminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController implements CommonProcess, ScriptExceptionProcess {


    private String tplCommon = "admin/member/";

    private final HttpServletRequest request;
    private final MemberListService infoService;


    //회원 관리 메인
    @GetMapping
    public String index(@ModelAttribute MemberSearch search, Model model) {
        commonProcess(model, "list");
        ListData<Member> data = infoService.getList(search);
        model.addAttribute("items", data.getContent());
        //model.addAttribute("pagination", data.getPageable());
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