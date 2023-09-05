package org.koreait.models.member;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class MemberNotfoundException extends CommonException {
    public MemberNotfoundException() {
        super(bundleValidation.getString("NotFound.member"), HttpStatus.BAD_REQUEST);
    }
}
