package com.ylz.springboot.modules.file.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.modules.file.service.FileManService;
import com.ylz.springboot.modules.file.vo.FileVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * MongoDb 上传下载文件
 *
 * @author lhh
 * @Date 2019/9/13 21:57
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileManService fileManService;

    @ApiOperation(value = "新增附件", notes = "新增附件")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseData save(FileVo vo) throws IOException {
        fileManService.save(vo);
        return ResponseData.successMessage("新增成功");
    }

    @ApiOperation(value = "查询", notes = "查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseData query(FileVo vo) throws IOException {
        return ResponseData.success(fileManService.findByPage(vo));
    }
}
