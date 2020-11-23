package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * ParamException
 *
 * @author: Chris
 * @time: 2019.02.14
 */
@Data
public class ParamException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_PARAM_ILLGAL;

    public ParamException(String message) {
        super(message);
    }
}
