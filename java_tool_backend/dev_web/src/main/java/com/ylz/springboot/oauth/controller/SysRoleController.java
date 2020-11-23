package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.service.SysRoleAclService;
import com.ylz.springboot.oauth.service.SysRoleService;
import com.ylz.springboot.oauth.service.SysRoleUserService;
import com.ylz.springboot.oauth.service.SysTreeService;
import com.ylz.springboot.oauth.vo.RoleVo;
import com.ylz.springboot.utils.StringSplitUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysRoleController
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Api(value = "角色管理模块", description = "角色管理模块提供对系统角色的增删改查")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @RequestMapping("/save")
    public ResponseData saveRole(RoleVo roleVo) {
        sysRoleService.save(roleVo);
        return ResponseData.successMessage("新建角色成功");
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @RequestMapping("/update")
    public ResponseData updateRole(RoleVo roleVo) {
        sysRoleService.update(roleVo);
        return ResponseData.successMessage("更新角色成功");
    }

    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @RequestMapping("/findAll")
    public ResponseData findAll() {
        return ResponseData.success(sysRoleService.findAll());
    }

    @ApiOperation(value = "分页查询角色", notes = "分页查询角色")
    @RequestMapping("/findByPage")
    public ResponseData findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return ResponseData.success(sysRoleService.findByPage(page, size, keyword));
    }

    @ApiOperation(value = "获取角色对应权限树", notes = "获取角色对应的权限树，标识出已有的权限")
    @RequestMapping("/roleTree")
    public ResponseData roleTree(@RequestParam("roleId") String roleId) {
        return ResponseData.success(sysTreeService.roleTree(roleId));
    }

    @ApiOperation(value = "更新角色对应的权限点列表", notes = "更新角色对应的权限点列表")
    @RequestMapping("/changeAcls")
    public ResponseData changeAcls(@RequestParam("roleId") String roleId, @RequestParam("aclIds") String aclIds) {
        List<String> aclIdList = StringSplitUtil.splitToListString(aclIds, ",");
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return ResponseData.successMessage("更新权限列表成功");
    }

    @ApiOperation(value = "更新角色对应的用户列表", notes = "更新角色对应的用户列表")
    @RequestMapping("/changeUsers")
    public ResponseData changeUsers(@RequestParam("roleId") String roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<String> userIdList = StringSplitUtil.splitToListString(userIds, ",");
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return ResponseData.success("更新用户列表成功");
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @RequestMapping("/delete")
    public ResponseData delete(String id) {
        sysRoleService.delete(id);
        return ResponseData.successMessage("删除角色成功");
    }
}
