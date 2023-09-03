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
/**
 * 회원가입 정보 유효성 검증을 위한 Validator 클래스.
 * PasswordValidator와 MobileValidator 인터페이스를 구현.
 */
public class JoinValidator implements Validator, PasswordValidator, MobileValidator {

    private final MemberRepository repository; // 회원 리포지토리

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinForm.class); // JoinForm 클래스를 지원하는지 확인
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinForm joinForm = (JoinForm) target; // 검증 대상 객체를 JoinForm으로 캐스팅

        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getMobile();

        // 아이디 중복 체크
        if (userId != null && !userId.isBlank() && repository.exists(userId)) {
            errors.rejectValue("userId", "Duplicate", "이미 사용 중인 아이디입니다.");
        }

        // 비밀번호 복잡성 체크
        if (userPw != null && !userPw.isBlank()
                && (!alphaCheck(userPw, true) || !numberCheck(userPw) || !specialCharsCheck(userPw))) {
            errors.rejectValue("userPw", "Complexity", "비밀번호는 영문, 숫자, 특수문자를 조합해야 합니다.");
        }

        // 비밀번호 확인
        if (userPw != null && !userPw.isBlank() && userPwRe != null && !userPwRe.isBlank()
                && !userPw.equals(userPwRe)) {
            errors.rejectValue("userPwRe", "Mismatch", "비밀번호가 일치하지 않습니다.");
        }

        // 휴대전화 번호 형식 체크
        if (mobile != null && !mobile.isBlank()) {
            mobile = mobile.replaceAll("\\D", "");
            joinForm.setMobile(mobile);

            if (!mobileNumCheck(mobile)) {
                errors.rejectValue("mobile", "Validate.mobile", "유효하지 않은 휴대전화 번호입니다.");
            }
        }
    }
}
