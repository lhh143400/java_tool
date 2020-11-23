package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.service.SysRoleAclService;
import com.ylz.springboot.oauth.service.SysRoleUserService;
import com.ylz.springboot.oauth.service.SysTreeService;
import com.ylz.springboot.oauth.service.SysUserService;
import com.ylz.springboot.oauth.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysUserController
 *
 * @author: Chris
 * @time: 2019.02.17
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping("/save")
    public ResponseData saveDept(UserVo userVo) {
        sysUserService.save(userVo);
        return ResponseData.successMessage("新增用户成功");
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @RequestMapping("/update")
    public ResponseData updateDept(UserVo userVo) {
        sysUserService.update(userVo);
        return ResponseData.successMessage("更新用户成功");
    }

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @RequestMapping("/findByPage")
    public ResponseData findByPage(@RequestParam(value = "deptId", required = false) String deptId,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageModel<UserVo> result = sysUserService.findByPage(page, size, deptId, keyword);
        return ResponseData.success(result);
    }

    @ApiOperation(value = "通过角色名称查询用户", notes = "通过角色名称查询用户")
    @RequestMapping("/findByRoleName")
    public ResponseData findByRoleName(@RequestParam(value = "deptId", required = false) String deptId,
                                       @RequestParam(value = "roleName", defaultValue = "") String roleName,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageModel<UserVo> result = sysUserService.findByRoleName(page, size, deptId, roleName);
        return ResponseData.success(result);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping("/delete")
    public ResponseData delete(String id) {
        sysUserService.delete(id);
        return ResponseData.successMessage("删除用户成功");
    }

    @ApiOperation(value = "校验用户密码", notes = "校验用户密码")
    @RequestMapping("/validate")
    public ResponseData validatePassword(String password) {
        UserVo userVo = RequestHolder.getCurrentUser();
        sysUserService.validatePassword(userVo, password);
        return ResponseData.successMessage("校验成功");
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @RequestMapping("/change")
    public ResponseData changePassword(String password) {
        UserVo userVo = RequestHolder.getCurrentUser();
        sysUserService.changePassword(userVo, password);
        return ResponseData.successMessage("密码修改成功，请重新登录");
    }

}
