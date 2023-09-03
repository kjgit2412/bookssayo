package org.koreait.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * 공통 유틸리티
 */
@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle bundle;
    private static ResourceBundle bundleValidation;
    private static ResourceBundle bundleError;

    private static Utils instance;

    private final HttpServletRequest request; // 요청 관련 정보를 처리하는 객체
    private final HttpSession session; // 세션을 관리하는 객체


    /**
     * 현재 접속한 장치가 모바일 장치인지 여부를 판단.
     *
     * @return 모바일 장치에 접속한 경우 true, 아닌 경우 false를 반환.
     */
    public boolean isMobile() {
        // User-Agent 헤더를 이용하여 모바일 장치 여부를 판단.
        boolean isMobile = request.getHeader("User-Agent").matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        /** 세션에 device 값이 이미 있는 경우 대체 */
        String device = (String)session.getAttribute("device");
        if (device != null && device.equals("mobile")) isMobile = true;

        return isMobile;
    }

    /**
     * PC와 Mobile 구분에 따른 템플릿 prefix 처리
     *
     * @param tpl 뷰 템플릿 파일명
     * @return 모바일 장치에 접속한 경우 "mobile/" 경로를, 그렇지 않은 경우 "front/" 경로를 반환.
     */
    public String view(String tpl) {
        String prefix = isMobile() ? "mobile/":"front/";
        return prefix + tpl;
    }

    /**
     * 메세지 조회
     *
     * @param code : 메세지 코드
     * @param type : validation, error, common
     * @return
     */
    public static String getMessage(String code, String type) {
        ResourceBundle _bundle = null;
        if (type.equals("validation")) { // 검증
            if (bundleValidation == null) bundleValidation = ResourceBundle.getBundle("messages.validations");
            _bundle = bundleValidation;
        } else if (type.equals("error")) { // 에러
            if (bundleError == null) bundleError = ResourceBundle.getBundle("messages.errors");
            _bundle = bundleError;
        } else { // 공통
            if (bundle == null) bundle = ResourceBundle.getBundle("messages.commons");
            _bundle = bundle;
        }

        try {
            return _bundle.getString(code);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 단일 요청 데이터 조회
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개 요청 데이터 조회
     *
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }


    public static int getNumber(int num, int defaultValue) {
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원 구분 UID
     * 비회원 구분은 IP + 브라우저 종류
     *
     */
    public int guestUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }
}