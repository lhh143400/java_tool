package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * NoResultException
 * 查询结果不存在异常
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
public class NoResultException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_GET_NO_RESULT;

    public NoResultException(String message) {
        super(message);
    }

}
