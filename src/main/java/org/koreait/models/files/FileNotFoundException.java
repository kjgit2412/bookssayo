package org.koreait.models.files;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {

    public FileNotFoundException() {
        super(bundleError.getString("NotFound.file"), HttpStatus.BAD_REQUEST);
    }
}
