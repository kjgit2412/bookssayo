package org.koreait.controllers.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberForm {

    private Long userNo;
    private String userId;
    private String userPw;
    private String userPwRe;
    @NotBlank
    private String userNm;

    @Email
    private String email;
    private String mobile;
}
