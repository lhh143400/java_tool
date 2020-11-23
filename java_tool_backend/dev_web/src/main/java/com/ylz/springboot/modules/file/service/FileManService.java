package com.ylz.springboot.modules.file.service;

import com.ylz.springboot.modules.file.pojo.FilePojo;
import com.ylz.springboot.modules.file.vo.FileVo;

import java.io.IOException;
import java.util.List;

/**
 * 文件管理
 *
 * @author lhh
 * @Date 2019/9/13 22:16
 */
public interface FileManService {

    /**
     * 保存
     */
    void save(FileVo vo) throws IOException;

    /**
     * 查询
     */
    List<FilePojo> findByPage(FileVo vo);

    /**
     * 删除
     *
     * @param id 主键
     */
    void del(String id);
}
