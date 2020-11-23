package com.ylz.springboot.oauth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ylz.springboot.commons.exception.UpdateOPException;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysRoleMapper;
import com.ylz.springboot.oauth.dao.SysRoleUserMapper;
import com.ylz.springboot.oauth.dao.SysUserMapper;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.pojo.SysRoleUser;
import com.ylz.springboot.oauth.pojo.SysUser;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.service.SysRoleUserService;
import com.ylz.springboot.oauth.vo.UserVo;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * SysRoleUserServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void changeRoleUsers(String roleId, List<String> userIdList) {
        List<String> oldUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (oldUserIdList.size() == userIdList.size()) {
            Set<String> oldUserIdSet = Sets.newHashSet(oldUserIdList);
            Set<String> userIdSet = Sets.newHashSet(userIdList);
            oldUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(oldUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId, userIdList);
        sysLogService.saveRoleUserLog(roleId, oldUserIdList, userIdList);
    }

    @Transactional(rollbackFor = UpdateOPException.class)
    public void updateRoleUsers(String roleId, List<String> userIdList) {
        try {
            sysRoleUserMapper.deleteByRoleId(roleId);

            if (CollectionUtils.isEmpty(userIdList)) {
                return;
            }
            List<SysRoleUser> roleUserList = Lists.newArrayList();
            for (String userId : userIdList) {
                SysRoleUser roleUser = SysRoleUser.builder()
                        .id(KeyGenerateUtil.generateOracleId())
                        .roleId(roleId).userId(userId)
                        .operator(RequestHolder.getCurrentUser().getUsername())
                        .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                        .operateTime(new Date())
                        .build();
                roleUserList.add(roleUser);
            }
            sysRoleUserMapper.batchInsert(roleUserList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateOPException("");
        }
    }

    @Override
    public List<UserVo> getUserListByRoleId(String roleId) {
        List<String> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        List<SysUser> sysUserList = sysUserMapper.findByIdList(userIdList);
        if (CollectionUtils.isEmpty(sysUserList)) {
            return Lists.newArrayList();
        }
        List<UserVo> userVoList = Lists.newArrayList();
        sysUserList.forEach(sysUser -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser, userVo);
            userVoList.add(userVo);
        });
        return userVoList;
    }

    @Override
    public List<SysRole> getRoleListByUserId(String userId) {
        List<String> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoleList = sysRoleMapper.findByIdList(roleIdList);
        if (CollectionUtils.isEmpty(sysRoleList)) {
            return Lists.newArrayList();
        }
        return sysRoleList;
    }
}
