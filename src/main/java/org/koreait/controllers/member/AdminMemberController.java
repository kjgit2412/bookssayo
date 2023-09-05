package org.koreait.controllers.member;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.commons.constants.Role;
import org.koreait.entities.Member;
import org.koreait.models.member.MemberInfoService;
import org.koreait.models.member.MemberListService;
import org.koreait.models.member.MemberSaveService;
import org.koreait.models.member.MemberSearch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("AdminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController implements CommonProcess, ScriptExceptionProcess {


    private String tplCommon = "admin/member/";

    private final Utils utils;
    private final HttpServletRequest request;
    private final MemberInfoService infoService;
    private final MemberListService listService;
    private final MemberSaveService saveService;


    //회원 관리 메인
    @GetMapping
    public String index(@ModelAttribute MemberSearch search, Model model) {
        commonProcess(model, "list");
        ListData<Member> data = listService.getList(search);
        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return tplCommon + "index";
    }
    @PostMapping
    public String indexPs(JoinForm form ,Model model, Errors errors) {
        commonProcess(model, "list");

        String mode = form.getMode();
        try {
            if(mode.equals("edit")) {
                saveService.save(form, errors);
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
        String pageTitle = "회원 목록";
        if (mode.equals("edit")) {
            pageTitle = "정보 수정";
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

        model.addAttribute("roles", Role.getList());
    }

    @GetMapping("/edit/{userNo}")
    public String edit(@PathVariable Long userNo, Model model) {
        commonProcess(model, "edit");
        JoinForm joinForm = infoService.getJoinForm(userNo);
        model.addAttribute("joinForm", joinForm);

        return tplCommon + "edit";
    }

    @PostMapping("/save")
    public String bookSave(@Valid JoinForm joinForm, Model model, Errors errors) {

        commonProcess(model, "save");

        String mode = joinForm.getMode();
        if (errors.hasErrors()) {
            return mode != null && mode.equals("edit") ? tplCommon + "edit" : tplCommon + "edit";
        }

        saveService.save(joinForm, errors);

        return "redirect:/admin/member";
    }

}