package org.koreait.commons.validators;

/**
 * 휴대폰 번호의 유효성을 검사하는 기능을 제공하는 인터페이스.
 */
public interface MobileValidator {

    /**
     * 1. 형식의 통일화 - 숫자가 아닌 문자 전부 제거 -> 숫자
     * 2. 패턴 생성 체크
     *
     * @param mobile 유효성을 검사할 휴대폰 번호
     * @return 유효한 번호인 경우 true, 그렇지 않은 경우 false
     */
    default boolean mobileNumCheck(String mobile) {
        mobile = mobile.replaceAll("\\D", ""); // 문자 제거하여 숫자만 남김
        String pattern = "^01[016]\\d{3,4}\\d{4}$"; // 휴대폰 번호 패턴 정의
        return mobile.matches(pattern); // 패턴에 맞는지 검사하여 결과 반환
    }
}
