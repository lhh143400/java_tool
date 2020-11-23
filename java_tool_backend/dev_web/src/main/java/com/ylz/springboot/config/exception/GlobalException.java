package com.ylz.springboot.config.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import lombok.Data;

/**
 * GlobalException
 *
 * @author: Chris
 * @time: 2019.02.14
 */
@Data
public class GlobalException extends RuntimeException {

    private ResponseCode responseCode = ResponseCode.ERROR;

    public GlobalException() {
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }

    protected GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
