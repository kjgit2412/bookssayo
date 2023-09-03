package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Member;
import org.koreait.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security의 UserDetailsService를 구현한 클래스
 * 사용자 정보를 데이터베이스에서 조회하여 UserDetails로 반환
 */
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository repository;

    /**
     * 사용자 아이디를 통해 회원 정보를 조회하여 UserDetails 객체로 반환
     * @param username 사용자 아이디
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자를 찾지 못한 경우 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUserId(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        // 사용자의 권한 정보를 생성
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(member.getRole().name())
        );

        // UserDetails 객체를 생성하여 반환
        return MemberInfo.builder()
                         .userNo(member.getUserNo())
                         .userId(member.getUserId())
                         .userPw(member.getUserPw())
                         .userNm(member.getUserNm())
                         .email(member.getEmail())
                         .mobile(member.getMobile())
                         .role(member.getRole())
                         .authorities(authorities)
                         .build();
    }
}