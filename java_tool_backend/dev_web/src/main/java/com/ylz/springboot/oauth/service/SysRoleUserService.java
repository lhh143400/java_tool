package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.vo.UserVo;

import java.util.List;

/**
 * SysRoleUserService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysRoleUserService {

    void changeRoleUsers(String roleId, List<String> userIdList);

    List<UserVo> getUserListByRoleId(String roleId);

    List<SysRole> getRoleListByUserId(String userId);
}
