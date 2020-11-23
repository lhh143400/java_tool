package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;

/**
 * RecoverOPException
 *
 * @author: Chris
 * @time: 2019.02.19
 */
public class RecoverOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_RECOVER_OP;

    public RecoverOPException(String message) {
        super(message);
    }

}
