package com.ylz.springboot.oauth.service.impl;

import com.google.common.collect.Lists;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.common.SystemConst;
import com.ylz.springboot.oauth.dao.SysAclMapper;
import com.ylz.springboot.oauth.dao.SysRoleAclMapper;
import com.ylz.springboot.oauth.dao.SysRoleUserMapper;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SysCoreServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    /**
     * 获取当前用户的权限点列表
     *
     * @return
     */
    @Override
    public List<SysAcl> getCurrentUserAclList() {
        String userId = RequestHolder.getCurrentUser().getId();
        return getAclListByUserId(userId);
    }

    /**
     * 获取对应用户所拥有的权限点
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysAcl> getAclListByUserId(String userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.selectAllAcl();
        }
        // 获取当前用户已分配的所有角色
        List<String> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        Set<String> aclIdSet = new HashSet<>();
        // 通过角色列表查询所有的权限点
        for (String roleId : roleIdList) {
            List<String> aclIdList = sysRoleAclMapper.findAclIdListByRoleId(roleId);
            if (CollectionUtils.isNotEmpty(aclIdList)) {
                aclIdSet.addAll(aclIdList);
            }
        }
        if (CollectionUtils.isEmpty(aclIdSet)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.findByAclIdList(Lists.newArrayList(aclIdSet));
    }

    /**
     * 获取对应角色所拥有的权限点
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysAcl> getAclListByRoleId(String roleId) {
        List<String> aclIdList = sysRoleAclMapper.findAclIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.findByAclIdList(aclIdList);
    }

    /**
     * 判断当前用户是否是超级管理员
     *
     * @return
     */
    private boolean isSuperAdmin() {
        String currentUsername = RequestHolder.getCurrentUser().getUsername();
        if (SystemConst.SUPER_ADMIN.contains(currentUsername)) {
            return true;
        }
        return false;
    }


}
