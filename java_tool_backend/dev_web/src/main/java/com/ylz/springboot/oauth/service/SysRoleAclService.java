package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.pojo.SysAcl;

import java.util.List;

/**
 * SysRoleAclService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysRoleAclService {

    void changeRoleAcls(String roleId, List<String> aclIdList);

    List<SysAcl> findAclListByRoleId(String roleId);
}
