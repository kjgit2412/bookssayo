package org.koreait.models.buyer;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BuyerNotFoundException extends CommonException {

    public BuyerNotFoundException() {
        super(bundleValidation.getString("NotFound.buyer"), HttpStatus.BAD_REQUEST);
    }
}
