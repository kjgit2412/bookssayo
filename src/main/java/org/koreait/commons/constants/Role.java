package org.koreait.commons.constants;

import org.koreait.commons.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * 회원 권한
 * enum 은 열거형 상수들의 집합. 배열보다 가독성이 뛰어남
 */
public enum Role {
    ADMIN, // 관리자 
    USER; // 일반회원


    public String getString() {
        return Utils.getMessage("MemberStatus." + name(), "common");
    }

    public static List<String[]> getList() {
        return Arrays.asList(
                new String[]{ADMIN.name(),ADMIN.getString()},
                new String[]{USER.name(),USER.getString()}
        );
    }
}
