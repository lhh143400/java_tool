package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.service.impl.SysLogServiceImpl;
import com.ylz.springboot.oauth.vo.SearchLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysLogController
 *
 * @author: Chris
 * @time: 2019.02.19
 */
@Api(value = "系统操作日志管理类", description = "记录系统管理模块的操作，提供误操作的还原功能")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogServiceImpl sysLogService;

    @ApiOperation(value = "还原操作", notes = "还原操作，对新增和删除操作不做还原")
    @RequestMapping("/recover")
    public ResponseData recover(@RequestParam("id") String id) {
        sysLogService.recover(id);
        return ResponseData.success("操作还原成功");
    }

    @ApiOperation(value = "分页查询操作日志", notes = "分页查询操作日志")
    @RequestMapping("/findByPage")
    public ResponseData findByPage(SearchLogVo searchLogVo,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseData.success(sysLogService.searchPageList(page, size, searchLogVo));
    }

}
