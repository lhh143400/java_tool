package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.pojo.SysAcl;

import java.util.List;

/**
 * SysCoreService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysCoreService {

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getAclListByUserId(String userId);

    List<SysAcl> getAclListByRoleId(String roleId);

}
