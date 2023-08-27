package org.koreait.commons.validators;

import org.koreait.commons.BadRequestException;

/**
 * 필수 입력 항목 체크
 *
 */
public interface RequiredValidator {
    default void requriedCheck(String str, String message) {
        if (str == null || str.isBlank()) {
            throw new BadRequestException(message);
        }
    }

    default void nullCheck(Object obj, String message) {
        if (obj == null) {
            throw new BadRequestException(message);
        }
    }
}
