package com.ylz.springboot.modules.mongodb.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.ylz.springboot.commons.consts.CommonConst;
import com.ylz.springboot.commons.exception.FileOpException;
import com.ylz.springboot.commons.exception.ParamException;
import com.ylz.springboot.modules.mongodb.service.MongoDbGridFsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Pattern;

/**
 * mongodb 文件上传下载
 *
 * @author lhh
 * @Date 2019/9/13 19:37
 */
@Service("MongoDbGridFsService")
public class MongoDbGridFsServiceImpl implements MongoDbGridFsService {

    /**
     * 图片格式校验正则
     */
    private static final Pattern IMAGE_PATTERN = Pattern.compile(".+(\\.bmp|\\.jpg|\\.png|\\.tif|\\.gif|\\.pcx|\\.exif|\\.fpx|\\.svg|\\.psd|\\.cdr|\\.pcd|\\.dxf|\\.ufo|\\.eps|\\.ai|\\.raw|\\.wmf|\\.webp)$", Pattern.CASE_INSENSITIVE);
    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 需要手动加入bean
     */
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @return 存储在MongoDB的文件ID
     */
    @Override
    public String save(InputStream inputStream, String fileName) {

        return save(inputStream, fileName, null);
    }

    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @param metadata    元数据
     * @return 存储在MongoDB的文件ID
     */
    @Override
    public String save(InputStream inputStream, String fileName, Object metadata) {
        String fileId;
        try {
            fileId = gridFsTemplate.store(inputStream, fileName, metadata).toString();
        } catch (Exception e) {
            throw new FileOpException("【文件上传错误】" + e.getMessage());
        }
        return fileId;
    }

    /**
     * 文件下载
     *
     * @param fileId   存储在MongoDB的文件ID
     * @param request  request
     * @param response response
     */
    @Override
    public void findById(String fileId, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(fileId)) {
            throw new ParamException("文件ID不能为空");
        }
        Query query = Query.query(Criteria.where("_id").is(fileId));
        GridFSFile file = gridFsTemplate.findOne(query);
        if (file == null) {
            throw new FileOpException("【文件下载错误】查无文件信息");
        }
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(file, gridFSDownloadStream);
        try {
            InputStream inputStream = gridFsResource.getInputStream();
            if(!isImage(file.getFilename())){
            response.addHeader("Content-Disposition", "attachment; filename=" + handleFileName(request, file.getFilename()));
            response.setContentType("application/octet-stream; charset=UTF-8");
            }
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            throw new FileOpException("【文件下载错误】" + e.getMessage());
        }
    }

    /**
     * 文件删除
     *
     * @param fileIdList 存储在MongoDB的文件ID集合
     */
    @Override
    public void delFile(List<String> fileIdList) {
        if (CollectionUtils.isEmpty(fileIdList)) {
            throw new ParamException("待删除文件ID不能为空");
        }
        try {
            for (String fileId : fileIdList) {
                Query query = Query.query(Criteria.where("_id").is(fileId));
                gridFsTemplate.delete(query);
            }
        } catch (Exception e) {
            throw new FileOpException("【文件删除错误】" + e.getMessage());
        }
    }


    /**
     * 解决下载文件名中文乱码
     *
     * @param request  HttpServletRequest
     * @param fileName 文件名
     * @return 处理后的文件名
     */
    public  String handleFileName(HttpServletRequest request, String fileName) {
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        boolean isIE = userAgent.contains("MSIE") || (userAgent.contains("GECKO") && userAgent.contains("RV:11"));
        try {
            if (isIE) {
                fileName = URLEncoder.encode(fileName, CommonConst.UTF_8);
            } else {
                fileName = new String(fileName.getBytes(CommonConst.UTF_8),CommonConst.ISO8859_1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 校验文件是否为图片格式
     *
     * @param fileName 文件名
     * @return 是：true 否：false
     */
    public static boolean isImage(String fileName) {
        return IMAGE_PATTERN.matcher(fileName).matches();
    }
}
