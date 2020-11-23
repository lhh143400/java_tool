package com.ylz.springboot.modules.mongodb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * mongodb 文件上传下载
 *
 * @author lhh
 * @Date 2019/9/13 19:35
 */
public interface MongoDbGridFsService {
    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @return 存储在MongoDB的文件ID
     */
    String save(InputStream inputStream, String fileName);

    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @param metadata    元数据
     * @return 存储在MongoDB的文件ID
     */
    String save(InputStream inputStream, String fileName, Object metadata);

    /**
     * 文件下载
     *
     * @param fileId   存储在MongoDB的文件ID
     * @param request  request
     * @param response response
     */
    void findById(String fileId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 文件删除
     *
     * @param fileIdList 存储在MongoDB的文件ID集合
     */
    void delFile(List<String> fileIdList);
}
