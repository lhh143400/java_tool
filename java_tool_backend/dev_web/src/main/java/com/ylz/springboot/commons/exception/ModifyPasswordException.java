package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;

/**
 * ModifyPasswordException
 *
 * @author: Chris
 * @time: 2019.02.22
 */
public class ModifyPasswordException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_MODIFY_PASSWORD;

    public ModifyPasswordException(String message) {
        super(message);
    }

}
