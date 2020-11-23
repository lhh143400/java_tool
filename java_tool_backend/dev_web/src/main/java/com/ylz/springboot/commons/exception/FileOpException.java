package com.ylz.springboot.commons.exception;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.config.exception.GlobalException;
import lombok.Data;

/**
 * @author lhh
 * @Date 2019/9/13 19:42
 */
@Data
public class FileOpException extends GlobalException {
    private ResponseCode responseCode=ResponseCode.ERROR_FILE_OP;

    public FileOpException(String message){
        super(message);
    }
}
