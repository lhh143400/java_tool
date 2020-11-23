package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * DuplicationException
 * 数据重复异常
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
public class DuplicationException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_DATA_DUPLICATION;

    public DuplicationException(String message) {
        super(message);
    }
}
