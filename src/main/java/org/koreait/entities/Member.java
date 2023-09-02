package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.Role;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
/**
 * 회원 정보를 나타내는 엔티티.
 * BaseEntity 클래스를 상속받아 사용하며, 데이터베이스 테이블과 매핑됩니다.
 */
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long userNo; // 회원의 고유 식별번호

    @Column(length=40, nullable = false, unique = true)
    private String userId; // 회원의 아이디, 중복 불가 및 최대 길이 제한

    @Column(length=65, nullable = false)
    private String userPw; // 회원의 비밀번호, 최대 길이 제한

    @Column(length=40, nullable = false)
    private String userNm; // 회원의 이름, 최대 길이 제한

    @Column(length=100, nullable = false)
    private String email; // 회원의 이메일, 최대 길이 제한

    @Column(length=11)
    private String mobile; // 회원의 휴대전화 번호

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role role = Role.USER; // 회원의 역할을 나타내는 상수 Enum 값, 기본값은 USER
}
