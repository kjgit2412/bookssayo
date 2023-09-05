package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.controllers.member.JoinForm;
import org.koreait.controllers.member.JoinValidator;
import org.koreait.entities.Member;
import org.koreait.models.books.BookSearch;
import org.koreait.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 회원 가입 정보를 저장하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository repository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    /**
     * 회원 가입 정보를 저장
     * @param joinForm 회원 가입 폼 정보
     * @param errors   유효성 검사 에러
     */
    public void save(JoinForm joinForm, Errors errors, @ModelAttribute BookSearch search) {
        // 회원 가입 폼 유효성 검사
        validator.validate(joinForm, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 회원 정보 매핑 및 저장
        Member member = new ModelMapper().map(joinForm, Member.class);
        member.setRole(Role.USER);

        // 비밀번호 해싱하여 저장
        String hash = encoder.encode(joinForm.getUserPw());
        member.setUserPw(hash);

        repository.saveAndFlush(member);
    }
}
