package org.koreait.controllers.admin;


import org.koreait.commons.CommonProcess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminBasicConfig")
@RequestMapping("/admin/config")
public class BasicConfigController implements CommonProcess {
    @GetMapping
    public String config(Model model) {
        commonProcess(model);

        return "admin/basic/index";
    }

    @PostMapping
    public String save(Model model) {
        commonProcess(model);

        return "redirect:/admin/config";
    }

    public void commonProcess(Model model) {
        CommonProcess.super.commonProcess(model, "사이트 설정");
        model.addAttribute("menuCode", "config");
    }
}
