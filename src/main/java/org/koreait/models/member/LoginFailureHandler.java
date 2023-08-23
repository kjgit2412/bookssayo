package org.koreait.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");

        HttpSession session = request.getSession();
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        session.removeAttribute("requiredUserId");
        session.removeAttribute("requiredUserPw");
        session.removeAttribute("globalError");

        session.setAttribute("userId", userId);

        if (userId == null || userId.isBlank()) {
            session.setAttribute("requiredUserId", bundle.getString("NotBlank.userId"));
        }

        if (userPw == null || userPw.isBlank()) {
            session.setAttribute("requiredUserPw", bundle.getString("NotBlank.userPw"));
        }

        if (userId != null && !userId.isBlank() && userPw != null && !userPw.isBlank()) {
            session.setAttribute("globalError", bundle.getString("Login.fail"));
        }

        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
