package org.koreait.commons;

import org.springframework.ui.Model;

/**
 * 컨트롤러 공통 처리
 *
 */
public interface CommonProcess {
    default void commonProcess(Model model, String pageTitle) {

        model.addAttribute("pageTitle", pageTitle);
    }
}
