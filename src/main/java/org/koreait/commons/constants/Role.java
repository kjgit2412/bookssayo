package org.koreait.commons.constants;

import org.koreait.commons.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * 회원 권한
 *
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
