package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.service.SysAclModuleService;
import com.ylz.springboot.oauth.service.SysTreeService;
import com.ylz.springboot.oauth.vo.AclModuleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SysAclModuleController
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Api(value = "权限模块管理模块", description = "权限模块管理包括对权限模块的增删改查")
@RestController
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @ApiOperation(value = "新增权限模块", notes = "新增权限模块的同时会默认增加该模块的访问权限点")
    @RequestMapping("/save")
    public ResponseData saveAclModule(AclModuleVo aclModuleVo) {
        sysAclModuleService.save(aclModuleVo);
        return ResponseData.successMessage("新增权限模块成功");
    }

    @ApiOperation(value = "更新权限模块", notes = "更新权限模块")
    @RequestMapping("/update")
    public ResponseData updateAclModule(AclModuleVo aclModuleVo) {
        sysAclModuleService.update(aclModuleVo);
        return ResponseData.successMessage("更新权限模块成功");
    }

    @ApiOperation(value = "获取权限模块树结构", notes = "获取权限模块树结构")
    @RequestMapping("/tree")
    public ResponseData tree() {
        return ResponseData.success(sysTreeService.aclModuleTree());
    }

    @ApiOperation(value = "删除权限模块", notes = "删除权限模块的同时会连同改权限模块的访问权限点一起删除")
    @RequestMapping("/delete")
    public ResponseData delete(@RequestParam("id") String id) {
        sysAclModuleService.delete(id);
        return ResponseData.successMessage("删除权限模块成功");
    }

}
