package com.ylz.springboot.modules.mongodb.controller;

import com.alibaba.fastjson.JSON;
import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.modules.mongodb.service.MongoDbGridFsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件上下传
 *
 * @author lhh
 * @Date 2019/9/13 20:09
 */
@RestController
@RequestMapping("/gridFs")
public class MongoDbGridFsController {
    @Autowired
    private MongoDbGridFsService fileService;

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseData upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return ResponseData.success(fileService.save(file.getInputStream(), file.getOriginalFilename()));
    }

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @RequestMapping(value = "/{fileId}")
    public void download(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        fileService.findById(fileId, request, response);
    }

    @ApiOperation(value = "文件删除", notes = "文件删除")
    @RequestMapping(value = "/delFile", method = RequestMethod.POST)
    public ResponseData delFile(String fileId) {
        List<String> listId = (List<String>) JSON.parse(fileId);
        fileService.delFile(listId);
        return ResponseData.successMessage("删除成功");
    }
}
