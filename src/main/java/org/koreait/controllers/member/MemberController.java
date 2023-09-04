package org.koreait.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.Utils;
import org.koreait.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
/**
 * 회원 관련 기능을 처리하는 컨트롤러 클래스.
 */
public class MemberController implements CommonProcess {

    private final Utils utils; // 공통 유틸리티 클래스
    private final MemberSaveService saveService; // 회원 저장 서비스 클래스

    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm joinForm, Model model) {
        commonProcess(model, "회원가입"); // 공통 처리 메소드 호출
        return utils.view("member/join"); // 회원가입 페이지의 뷰 이름 반환
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {
        commonProcess(model, "회원가입"); // 공통 처리 메소드 호출

        // 회원가입 처리
        saveService.save(joinForm, errors);

        if (errors.hasErrors()) {
            return utils.view("member/join"); // 유효성 오류가 있는 경우 회원가입 페이지로 이동
        }

        return "redirect:/member/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess(model, "로그인"); // 공통 처리 메소드 호출

        return utils.view("member/login"); // 로그인 페이지의 뷰 이름 반환
    }
}
