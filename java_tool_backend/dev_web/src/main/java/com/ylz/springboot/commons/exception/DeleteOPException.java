package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * DeleteOPException
 * 删除操作异常
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
public class DeleteOPException extends GlobalException {

    private ResponseCode responseCode = ResponseCode.ERROR_DELETE_OP;

    public DeleteOPException(String message) {
        super(message);
    }

}
