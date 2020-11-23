package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * UpdateOPException
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
public class UpdateOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_UPDATE_OP;

    public UpdateOPException(String message) {
        super(message);
    }

}
