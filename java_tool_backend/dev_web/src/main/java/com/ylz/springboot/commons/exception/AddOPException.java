package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * AddOPException
 *
 * @author: Chris
 * @time: 2019.02.20
 */
@Data
public class AddOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_ADD_OP;

    public AddOPException(String message) {
        super(message);
    }

}
