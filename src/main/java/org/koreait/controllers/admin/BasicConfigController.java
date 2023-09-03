package org.koreait.controllers.admin;

import org.koreait.commons.CommonProcess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 기본 설정 컨트롤러 클래스.
 */
@Controller("adminBasicConfig")
@RequestMapping("/admin/config")
public class BasicConfigController implements CommonProcess {

    /**
     * 기본 설정 페이지를 보여주는 메소드.
     */
    @GetMapping
    public String config(Model model) {
        commonProcess(model); // 공통 처리 메소드 호출

        return "admin/basic/index"; // 관리자 기본 설정 페이지의 뷰 이름 반환
    }

    /**
     * 설정을 저장하고 설정 페이지로 리다이렉트하는 메소드.
     * @param model Model 객체
     * @return 관리자 기본 설정 페이지로 리다이렉트하는 URL
     */
    @PostMapping
    public String save(Model model) {
        commonProcess(model); // 공통 처리 메소드 호출

        return "redirect:/admin/config"; // 관리자 기본 설정 페이지로 리다이렉트하는 URL 반환
    }

    /**
     * 공통 처리를 위한 메소드입니다. 공통 프로세스를 수행하고 모델에 데이터를 추가
     * @param model Model 객체
     */
    public void commonProcess(Model model) {
        CommonProcess.super.commonProcess(model, "사이트 설정"); // CommonProcess의 공통 처리 메소드 호출
        model.addAttribute("menuCode", "config"); // 모델에 "menuCode" 속성 추가
    }
}
