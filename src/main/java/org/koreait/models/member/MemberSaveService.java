package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.controllers.admin.MemberForm;
import org.koreait.controllers.member.JoinForm;
import org.koreait.controllers.member.JoinValidator;
import org.koreait.entities.Member;
import org.koreait.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

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
    public void save(JoinForm joinForm, Errors errors) {

        Long userNo = joinForm.getUserNo();
        Member member = null;

        if (userNo != null) {
            member = repository.findById(userNo).orElseThrow(MemberNotfoundException::new);
        }else {
            member = new Member();
            System.out.println("userNo1 = " + userNo);
        }

        // 회원 가입 폼 유효성 검사
        validator.validate(joinForm, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 회원 정보 매핑 및 저장
        member = new ModelMapper().map(joinForm, Member.class);
        member.setRole(Role.USER);

        // 비밀번호 해싱하여 저장
        String hash = encoder.encode(joinForm.getUserPw());
        member.setUserPw(hash);

        repository.saveAndFlush(member);
        joinForm.setUserNo(member.getUserNo());
    }

    public void update(MemberForm form, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        String userId = form.getUserId();
        String userPw = form.getUserPw();
        String userPwRe = form.getUserPwRe();
        if (userId != null && !userId.isBlank()) {
            Member member = repository.findByUserId(userId);
            System.out.println(member);
            if (member == null) return;
            member.setUserNm(form.getUserId());
            member.setEmail(form.getEmail());
            member.setMobile(form.getMobile());

            if (userPw != null && userPwRe != null && !userPw.isBlank() && !userPwRe.isBlank()) {
                if (!userPw.equals(userPwRe)) {
                    errors.rejectValue("userPwRe", "Mismatch");
                }

                member.setUserPw(encoder.encode(userPw));
            }
        }
        repository.flush();
    }
}
