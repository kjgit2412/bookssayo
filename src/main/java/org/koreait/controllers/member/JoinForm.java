package org.koreait.controllers.member;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
/**회원가입 양식 */
@Data
public class JoinForm {
    private String mode;

    private Long userNo; // 회원 번호 (수정 시에만 사용)

    @NotBlank
    @Size(min=6)
    private String userId;

    @NotBlank
    @Size(min=8)
    private String userPw;

    @NotBlank
    private String userPwRe;

    @NotBlank
    private String userNm;

    @Email
    private String email;

    private String mobile;

    @AssertTrue
    private boolean agree = true;
}
