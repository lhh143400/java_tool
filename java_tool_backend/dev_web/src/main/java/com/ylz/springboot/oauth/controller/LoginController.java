package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseCode;
import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.dto.AclModuleLevelDto;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.pojo.SysUser;
import com.ylz.springboot.oauth.service.*;
import com.ylz.springboot.oauth.vo.UserLoginVo;
import com.ylz.springboot.oauth.vo.UserVo;
import com.ylz.springboot.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoginController
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Api(value = "LoginController", description = "用户登录、退出、获取用户信息以及获取菜单权限")
@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysCoreService sysCoreService;

    @ApiOperation(value = "登录接口", notes = "用户登录接口，登录之后才可访问其他接口")
    @RequestMapping("/login")
    public ResponseData login(UserLoginVo userLoginVo, HttpSession session) {
        String username = userLoginVo.getUsername();
        String password = userLoginVo.getPassword();
        SysUser sysUser = sysUserService.findByKeyword(username);

        if (StringUtils.isBlank(username)) {
            return ResponseData.errorMessage("用户名不能为空");
        } else if (StringUtils.isBlank(password)) {
            return ResponseData.errorMessage("登陆密码不能为空");
        } else if (sysUser == null) {
            return ResponseData.errorMessage("查询不到指定用户");
        } else if (!sysUser.getPassword().equals(MD5Util.encode(password))) {
            return ResponseData.errorMessage("用户名或密码错误");
        } else if (sysUser.getStatus() != 1) {
            return ResponseData.errorMessage("用户已被冻结，请联系管理员");
        } else {    // LOGIN SUCCESS
            List<SysRole> roles = sysRoleUserService.getRoleListByUserId(sysUser.getId());
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser, userVo);
            userVo.setRoles(roles);
            session.setAttribute("user", userVo);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", session.getId());
        tokenMap.put("userId", sysUser.getId());
        return ResponseData.success(tokenMap);
    }

    @ApiOperation(value = "用户登出接口", notes = "登出操作清空session信息")
    @RequestMapping("/logout")
    public ResponseData logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return ResponseData.successMessage("登出成功");
    }

    @ApiOperation(value = "用户无权限访问时跳转的接口", notes = "用户无权限访问时跳转的接口")
    @RequestMapping("/unauth")
    public ResponseData unauth() {
        return ResponseData.error(ResponseCode.ERROR_LOGIN_NOAUTH.getCode(),
                ResponseCode.ERROR_LOGIN_NOAUTH.getDesc());
    }

    @ApiOperation(value = "获取用户菜单权限", notes = "获取用户菜单权限")
    @RequestMapping("/functions")
    public ResponseData aclModules() {
        return ResponseData.success(sysTreeService.functionTree());
    }

    @ApiOperation(value = "获取用户基本信息", notes = "获取用户基本信息")
    @RequestMapping("/info")
    public ResponseData getInfo(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        List<SysAcl> aclList = sysCoreService.getCurrentUserAclList();
        userVo.setAcls(aclList);
        return ResponseData.success(userVo);
    }

    @ApiOperation(value = "获取用户的权限模块和权限点", notes = "获取用户的权限模块和权限点")
    @RequestMapping("/acls")
    public ResponseData getAcls(HttpSession session) {
//        UserVo userVo = (UserVo) session.getAttribute("user");
//        Map<String, List<AclModuleLevelDto>> map = new HashMap<>();
//        if (CollectionUtils.isNotEmpty(userVo.getRoles())) {
//            userVo.getRoles().forEach(role -> {
//                List<AclModuleLevelDto> roleTree = sysTreeService.roleTree(role.getId());
//                map.put(role.getName(), roleTree);
//            });
//        }
//        return ResponseData.success(map);
        return ResponseData.success(sysCoreService.getCurrentUserAclList());
    }

}