package com.ylz.springboot.modules.file.service.impl;

import com.ylz.springboot.commons.exception.ParamException;
import com.ylz.springboot.modules.file.pojo.FilePojo;
import com.ylz.springboot.modules.file.service.FileManService;
import com.ylz.springboot.modules.file.vo.FileVo;
import com.ylz.springboot.modules.mongodb.service.MongoDbGridFsService;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.utils.KeyGenerateUtil;
import com.ylz.springboot.utils.PropertiesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 文件管理--实现层
 *
 * @author lhh
 * @Date 2019/9/13 22:19
 */
@Service("FileManService")
public class FileManServiceImpl implements FileManService{

   @Autowired
   private MongoDbGridFsService mongoDbGridFsService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(FileVo vo) throws IOException {
        MultipartFile[] fileList = vo.getFileList();
        boolean fileListEmpty = fileList == null || fileList.length == 0;
        if (fileListEmpty) {
            throw new ParamException("附件不能为空");
        }
        String fileUrlPrefix = PropertiesUtil.getString("backend.url-prefix") + "/gridFs/";
        for (MultipartFile file : fileList) {
            FilePojo pojo = new FilePojo();
            BeanUtils.copyProperties(vo,pojo);
            pojo.setId( KeyGenerateUtil.generateOracleId());
            pojo.setFileName(file.getOriginalFilename());
            pojo.setFileSize(formatFileSize(file.getSize()));
            pojo.setFileType(file.getContentType());
            // 上传文件到MongoDB
            String fileId = mongoDbGridFsService.save(file.getInputStream(), file.getOriginalFilename());
            pojo.setFileUrl(fileUrlPrefix + fileId);
            pojo.setFileId(fileId);
            pojo.setUploadTime(new Date());
            pojo.setUploadUserId(RequestHolder.getCurrentUser().getId());

            mongoTemplate.insert(pojo);

        }
    }

    /**
     * 查询
     *
     * @param vo
     */
    @Override
    public List<FilePojo> findByPage(FileVo vo) {
        List<FilePojo> all = mongoTemplate.findAll(FilePojo.class);
        return all;
    }


    /**
     * 删除
     *
     * @param id 主键
     */
    @Override
    public void del(String id) {

    }


    /**
     * 格式化文件大小
     *
     * @param fileSize 文件大小
     * @return 格式化后的文件大小字符串
     */
    private String formatFileSize(Long fileSize) {
        StringBuilder result = new StringBuilder();
        double num = 1024;
        boolean m = fileSize >= num * num;
        boolean k = fileSize >= num;
        if (m) {
            result.append(String.format("%.1f", fileSize.doubleValue() / num / num)).append("M");
        } else if (k) {
            result.append(String.format("%.1f", fileSize.doubleValue() / num)).append("K");
        } else {
            result.append(fileSize).append("B");
        }
        return result.toString();
    }
}



