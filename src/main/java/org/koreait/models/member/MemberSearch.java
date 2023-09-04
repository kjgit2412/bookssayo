package org.koreait.models.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.Role;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearch {
    private int page = 1; // 페이지 번호
    private int limit = 20; // 1페이지당 레코드 갯수

    private List<Role> statuses;
    private Role status;

    private Long userNo; // 사용자 번호
    private String userId; // 아이디
    private String userNm; // 이름
    private String email; //이메일

    private String sopt;    // 검색 옵션
    private String skey;    // 검색 키워드

    private String sort; // 정렬
}
