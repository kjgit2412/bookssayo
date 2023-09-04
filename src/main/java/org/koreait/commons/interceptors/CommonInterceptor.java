package org.koreait.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 공통 처리 인터셉터
 * - 로그인 실패시 세션에 남아 있는 데이터 삭제
 * - 현재 접속한 장비 체크하여 템플릿 분리 설정
 */
@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final HttpServletRequest request;
    private final Utils utils;

    /**
     * 요청 처리 전에 실행되는 메소드
     * @param request  현재 요청 객체
     * @param response 현재 응답 객체
     * @param handler  요청 처리를 수행하는 핸들러
     * @return 요청 처리를 계속할지 여부 (true: 계속, false: 중지)
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init();
        return true;
    }

    /**
     * 초기화 메소드
     */
    private void init() {
        HttpSession session = request.getSession();
        String URI = request.getRequestURI();

        // 1. 로그인 실패시 세션에 남아 있는 데이터 삭제
        if (URI.indexOf("/member/login") == -1) {
            // 로그인이 아닌 경로로 접속한 경우 불필요한 세션 데이터 삭제
            session.removeAttribute("userId");
            session.removeAttribute("requiredUserId");
            session.removeAttribute("requiredUserPw");
            session.removeAttribute("globalError");
        }

        // 2. 현재 접속한 장비 체크하여 템플릿 분리 설정
        String device = utils.isMobile() ? "mobile" : "pc";
        String _device = request.getParameter("device");

        // URL에 ?device=mobile이 있는 경우
        device = _device == null ? device : _device.equals("mobile") ? "mobile" : "pc";
        session.setAttribute("device", device);
    }
}
