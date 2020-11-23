package com.ylz.springboot.oauth.service.impl;

import com.ylz.springboot.commons.exception.*;
import com.ylz.springboot.oauth.common.AclConst;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysAclMapper;
import com.ylz.springboot.oauth.dao.SysAclModuleMapper;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysAclModule;
import com.ylz.springboot.oauth.service.SysAclModuleService;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.vo.AclModuleVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import com.ylz.springboot.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * SysAclModuleServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    @Transactional(rollbackFor = AddOPException.class)
    public void save(AclModuleVo aclModuleVo) {
        try {
            BeanValidatorUtil.check(aclModuleVo);
            if (checkExist(aclModuleVo.getId(), aclModuleVo.getParentId(), aclModuleVo.getName())) {
                throw new DuplicationException("同一层级下不能存在相同名称的权限模块");
            }
            if (checkUrlExist(aclModuleVo.getModuleUrl(), null)) {
                throw new DuplicationException("当前URL已被使用");
            }
            SysAclModule aclModule = SysAclModule.builder()
                    .id(KeyGenerateUtil.generateOracleId())
                    .name(aclModuleVo.getName()).parentId(aclModuleVo.getParentId())
                    .seq(aclModuleVo.getSeq()).status(aclModuleVo.getStatus())
                    .remark(aclModuleVo.getRemark())
                    .moduleUrl(aclModuleVo.getModuleUrl())
                    .build();
            // 计算当前部门层级
            aclModule.setFullLevel(LevelUtil.calculateLevel(getParentLevel(aclModuleVo.getParentId()), aclModuleVo.getParentId()));
            aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
            aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            aclModule.setOperateTime(new Date());

            // 添加权限模块时默认预增加模块访问权限点
            SysAcl sysAcl = SysAcl.builder()
                    .id(KeyGenerateUtil.generateOracleId())
                    .name(aclModule.getName() + AclConst.ACL_NAME_DEFAULT_SUFFIX)
                    .code(AclConst.ACL_CODE_DEFAULT)
                    .aclModuleId(aclModule.getId())
                    .seq(AclConst.ACL_SEQ_TOP)
                    .remark(aclModule.getName() + AclConst.ACL_NAME_DEFAULT_SUFFIX)
                    .status(AclConst.ACL_STATUS_ENABLE).type(AclConst.ACL_TYPE_MENU)
                    .url(aclModule.getModuleUrl())
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date())
                    .build();

            sysAclModuleMapper.insertSelective(aclModule);
            sysAclMapper.insertSelective(sysAcl);

            sysLogService.saveAclModuleLog(null, aclModule);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddOPException("新增权限模块操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = {DuplicationException.class, NoResultException.class})
    public void update(AclModuleVo aclModuleVo) {
        BeanValidatorUtil.check(aclModuleVo);
        if (checkExist(aclModuleVo.getId(), aclModuleVo.getParentId(), aclModuleVo.getName())) {
            throw new DuplicationException("同一层级下不能存在相同名称的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(aclModuleVo.getId());
        if (before == null) {
            throw new NoResultException("待更新的权限模块不存在");
        }
        SysAclModule after = SysAclModule.builder()
                .id(aclModuleVo.getId())
                .name(aclModuleVo.getName()).parentId(aclModuleVo.getParentId())
                .seq(aclModuleVo.getSeq()).status(aclModuleVo.getStatus())
                .remark(aclModuleVo.getRemark())
                .moduleUrl(aclModuleVo.getModuleUrl())
                .build();
        // 计算当前部门层级
        after.setFullLevel(LevelUtil.calculateLevel(getParentLevel(aclModuleVo.getParentId()), aclModuleVo.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        // 权限模块访问URL如果有变化，需要更新默认增加的权限点
        if (!before.getModuleUrl().equals(after.getModuleUrl())) {
            String moduleUrl = after.getModuleUrl();
            SysAcl oldAcl = sysAclMapper.findByAclModuleIdAndTypeAndCode(after.getId(), AclConst.ACL_TYPE_MENU, AclConst.ACL_CODE_DEFAULT);

            if (checkUrlExist(moduleUrl, oldAcl.getId())) {
                throw new DuplicationException("当前URL已被使用");
            }

            SysAcl newAcl = SysAcl.builder().id(oldAcl.getId())
                    .url(moduleUrl)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date())
                    .build();
            sysAclMapper.updateByPrimaryKeySelective(newAcl);

        }
        recursiveUpdateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
    }

    /**
     * 递归更新权限模块和子权限模块
     *
     * @param before
     * @param after
     */
    @Transactional(rollbackFor = UpdateOPException.class)
    public void recursiveUpdateWithChild(SysAclModule before, SysAclModule after) {
        try {
            String newLevelPrefix = after.getFullLevel();
            String oldLevelPrefix = before.getFullLevel();
            // 如果更新后的权限模块层级与更新前的权限模块层级发生变化，需要更新子权限模块的层级
            if (!newLevelPrefix.equals(oldLevelPrefix)) {
                List<SysAclModule> aclModuleList = sysAclModuleMapper.selectChildAclModuleByLevel(before.getFullLevel(), before.getId());
                if (CollectionUtils.isNotEmpty(aclModuleList)) {
                    aclModuleList.forEach(beforeAclModule -> {
                        if (beforeAclModule.getFullLevel().indexOf(oldLevelPrefix) == 0) {
                            String newLevel = newLevelPrefix + beforeAclModule.getFullLevel().substring(oldLevelPrefix.length());
                            // 构造更新的AclModule，子AclModule只做level的更新
                            SysAclModule afterAclModule = SysAclModule.builder()
                                    .id(beforeAclModule.getId())
                                    .name(beforeAclModule.getName()).parentId(beforeAclModule.getParentId())
                                    .seq(beforeAclModule.getSeq()).status(beforeAclModule.getStatus())
                                    .remark(beforeAclModule.getRemark())
                                    .moduleUrl(beforeAclModule.getModuleUrl())
                                    .build();
                            afterAclModule.setFullLevel(newLevel);
                            afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                            afterAclModule.setOperateIp(RequestHolder.getCurrentRequest().getRemoteAddr());
                            afterAclModule.setOperateTime(new Date());

                            recursiveUpdateWithChild(beforeAclModule, afterAclModule);
                        }
                    });
                }
            }
            sysAclModuleMapper.updateByPrimaryKeySelective(after);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateOPException("更新权限模块操作出错, 错误原因: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = DeleteOPException.class)
    public void delete(String aclModuleId) {
        try {
            SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
            if (sysAclModule == null) {
                throw new NoResultException("待删除的权限模块不存在");
            }
            if (sysAclModuleMapper.countByParentId(sysAclModule.getId()) > 0) {
                throw new DeleteOPException("当前权限模块下面有子权限模块，无法删除");
            }
            // 判断当前权限模块下是否有权限点（不包含默认权限点）
            if (sysAclMapper.countByAclModuleIdAndCode(sysAclModule.getId(), AclConst.ACL_CODE_DEFAULT) > 0) {
                throw new DeleteOPException("当前权限模块下面有权限点，无法删除");
            }
            sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
            // 关联删除默认增加的权限点
            sysAclMapper.deleteByAclModuleIdAndCode(aclModuleId, AclConst.ACL_CODE_DEFAULT);
            sysLogService.saveAclModuleLog(sysAclModule, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeleteOPException("删除权限模块操作出错，错误原因: " + e.getMessage());
        }
    }

    /**
     * 判断当前层级下是否有相同名称的权限模块
     *
     * @param aclModuleId
     * @param parentId
     * @param aclModuleName
     * @return
     */
    private boolean checkExist(String aclModuleId, String parentId, String aclModuleName) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    private boolean checkUrlExist(String url, String id) {
        return sysAclMapper.countByUrl(url, id) > 0;
    }

    private String getParentLevel(String aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getFullLevel();
    }
}
