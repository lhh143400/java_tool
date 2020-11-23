package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.service.SysAclService;
import com.ylz.springboot.oauth.vo.AclVo;
import com.ylz.springboot.oauth.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * SysAclController
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Api(value = "权限点管理模块", description = "权限点管理模块包括对权限点的增删改查")
@RestController
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    @ApiOperation(value = "新增权限点", notes = "新增权限点")
    @RequestMapping("/save")
    public ResponseData saveAcl(AclVo aclVo) {
        sysAclService.save(aclVo);
        return ResponseData.successMessage("新增权限点成功");
    }

    @ApiOperation(value = "更新权限点", notes = "更新权限点")
    @RequestMapping("/update")
    public ResponseData updateAcl(AclVo aclVo) {
        sysAclService.update(aclVo);
        return ResponseData.successMessage("更新权限点成功");
    }

    @ApiOperation(value = "查询权限点", notes = "查询权限点")
    @RequestMapping("/findByPage")
    public ResponseData findByPage(@RequestParam(value = "aclModuleId", required = false) String aclModuleId,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseData.success(sysAclService.findByPage(page, size, aclModuleId, keyword));
    }

    @ApiOperation(value = "删除权限点", notes = "删除权限点")
    @RequestMapping("/delete")
    public ResponseData delete(String id) {
        sysAclService.delete(id);
        return ResponseData.successMessage("删除权限点成功");
    }

}
