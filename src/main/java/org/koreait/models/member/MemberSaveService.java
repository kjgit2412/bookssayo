package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.controllers.member.JoinForm;
import org.koreait.controllers.member.JoinValidator;
import org.koreait.entities.Member;
import org.koreait.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository repository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    public void save(JoinForm joinForm, Errors errors) {
        validator.validate(joinForm, errors);
        if (errors.hasErrors()) {
            return;
        }

        Member member = new ModelMapper().map(joinForm, Member.class);
        member.setRole(Role.USER);

        String hash = encoder.encode(joinForm.getUserPw());
        member.setUserPw(hash);

        repository.saveAndFlush(member);
    }
}
