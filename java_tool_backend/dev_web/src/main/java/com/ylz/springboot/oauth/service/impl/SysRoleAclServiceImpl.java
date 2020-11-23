package com.ylz.springboot.oauth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ylz.springboot.commons.exception.UpdateOPException;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysAclMapper;
import com.ylz.springboot.oauth.dao.SysRoleAclMapper;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysRoleAcl;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.service.SysRoleAclService;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * SysRoleAclServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void changeRoleAcls(String roleId, List<String> aclIdList) {
        List<String> oldAclIdList = sysRoleAclMapper.findAclIdListByRoleId(roleId);
        if (oldAclIdList.size() == aclIdList.size()) {
            Set<String> oldAclIdSet = Sets.newHashSet(oldAclIdList);
            Set<String> aclIdSet = Sets.newHashSet(aclIdList);
            oldAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(oldAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        sysLogService.saveRoleAclLog(roleId, oldAclIdList, aclIdList);
    }

    @Transactional(rollbackFor = UpdateOPException.class)
    public void updateRoleAcls(String roleId, List<String> aclIdList) {
        try {
            // 删除原来数据
            sysRoleAclMapper.deleteByRoleId(roleId);

            if (CollectionUtils.isEmpty(aclIdList)) {
                return;
            }
            List<SysRoleAcl> roleAclList = Lists.newArrayList();
            for (String aclId : aclIdList) {
                SysRoleAcl roleAcl = SysRoleAcl.builder()
                        .id(KeyGenerateUtil.generateOracleId())
                        .roleId(roleId).aclId(aclId)
                        .operator(RequestHolder.getCurrentUser().getUsername())
                        .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                        .operateTime(new Date())
                        .build();
                roleAclList.add(roleAcl);
            }
            sysRoleAclMapper.batchInsert(roleAclList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateOPException("更新角色-权限操作出错, 错误原因: " + e.getMessage());
        }
    }

    @Override
    public List<SysAcl> findAclListByRoleId(String roleId) {
        List<String> aclIdList = sysRoleAclMapper.findAclIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        List<SysAcl> aclList = sysAclMapper.findByAclIdList(aclIdList);
        return aclList;
    }

}
