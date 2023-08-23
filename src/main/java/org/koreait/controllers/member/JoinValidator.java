package org.koreait.controllers.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.validators.MobileValidator;
import org.koreait.commons.validators.PasswordValidator;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator, MobileValidator {
    private final MemberRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinForm joinForm = (JoinForm)target;

        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getMobile();

        // 아이디 중복 체크
        if (userId != null && !userId.isBlank() && repository.exists(userId)) {
            errors.rejectValue("userId", "Duplicate");
        }

        // 비밀번호 복잡성 체크
        if (userPw != null && !userPw.isBlank()
            && (!alphaCheck(userPw, true) || !numberCheck(userPw) || !specialCharsCheck(userPw))) {
            errors.rejectValue("userPw", "Complexity");
        }

        // 비밀번호 확인
        if (userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank()
            && !userPw.equals(userPwRe)) {
            errors.rejectValue("userPwRe", "Mismatch");
        }

        // 휴대전화 번호 형식 체크
        if (mobile != null && !mobile.isBlank()) {
            mobile = mobile.replaceAll("\\D", "");
            joinForm.setMobile(mobile);

            if (!mobileNumCheck(mobile)) {
                errors.rejectValue("mobile", "Validate.mobile");
            }
        }
    }
}
