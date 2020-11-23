package com.ylz.springboot.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lhh
 * @Date 2019/11/11 0:20
 */
public interface PoiServices {


    /**
     * poi文件上传
     */
    void imp(MultipartFile excel);
}
