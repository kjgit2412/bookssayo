package org.koreait.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.Role;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity {
    private Long userNo;
    private String userId;
    private String userPw;
    private String userNm;
    private String email;
    private String mobile;
    private Role role = Role.USER;
}
