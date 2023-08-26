package org.koreait.commons;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static List<MenuDetail> gets(String code) {
        List<MenuDetail> menus = new ArrayList<>();

        // 회원 하위 메뉴
        if (code.equals("member")) {
            menus.add(new MenuDetail("member", "회원 목록", "/admin/member"));
        } else if (code.equals("book")) { // 상품관리 하위 메뉴
            menus.add(new MenuDetail("book", "도서목록", "/admin/book"));
            menus.add(new MenuDetail("add", "도서등록", "/admin/book/add"));
            menus.add(new MenuDetail("category", "도서분류", "/admin/book/category"));
        }

        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request) {
        String URI = request.getRequestURI();

        return URI.substring(URI.lastIndexOf('/') + 1);
    }
}