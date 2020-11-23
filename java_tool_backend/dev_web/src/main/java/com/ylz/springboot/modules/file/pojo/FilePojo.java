package com.ylz.springboot.modules.file.pojo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 文件管理-数据库
 *
 * @author lhh
 * @Date 2019/9/13 21:59
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilePojo {
    /**
     * 附件ID
     */
    private String id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件路径
     */
    private String fileUrl;
    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 上传人
     */
    private String uploadUserId;

    /**
     * 备注
     */
    private String fileDesc;
}
