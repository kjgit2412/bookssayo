package org.koreait.models.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.commons.constants.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 회원 정보를 담는 클래스입니다. Spring Security의 UserDetails 인터페이스를 구현하여 사용자 정보와 권한 정보를 제공
 */
@Data @Builder
public class MemberInfo implements UserDetails {
    private Long userNo;
    private String userId;
    private String userPw;
    private String userNm;
    private String email;
    private String mobile;
    private Role role;

    private Collection<GrantedAuthority>  authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
