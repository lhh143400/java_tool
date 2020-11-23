package com.ylz.springboot.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.ylz.springboot.commons.exception.NoResultException;
import com.ylz.springboot.commons.exception.RecoverOPException;
import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.common.LogConst;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.*;
import com.ylz.springboot.oauth.pojo.*;
import com.ylz.springboot.oauth.service.*;
import com.ylz.springboot.oauth.vo.AclModuleVo;
import com.ylz.springboot.oauth.vo.DeptVo;
import com.ylz.springboot.oauth.vo.SearchLogVo;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SysLogService
 *
 * @author: Chris
 * @time: 2019.02.14
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;
    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Override
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        // 不记录用户密码
        if (before != null) {
            before.setPassword("******");
        }
        if (after != null) {
            after.setPassword("******");
        }
        sysLog.setType(LogConst.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleAclLog(String roleId, List<String> before, List<String> after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleUserLog(String roleId, List<String> before, List<String> after) {
        SysLog sysLog = new SysLog();
        sysLog.setId(KeyGenerateUtil.generateOracleId());
        sysLog.setType(LogConst.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JSON.toJSONString(before));
        sysLog.setNewValue(after == null ? "" : JSON.toJSONString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(LogConst.LOG_STATUS_NORECOVER);
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public PageModel<SysLog> searchPageList(Integer page, Integer size, SearchLogVo searchLogVo) {
        SearchLogVo dto = new SearchLogVo();
        dto.setType(searchLogVo.getType());
        if (StringUtils.isNotBlank(searchLogVo.getOldValue())) {
            dto.setOldValue("%" + searchLogVo.getOldValue() + "%");
        }
        if (StringUtils.isNotBlank(searchLogVo.getNewValue())) {
            dto.setNewValue("%" + searchLogVo.getNewValue() + "%");
        }
        if (StringUtils.isNotBlank(searchLogVo.getOperator())) {
            dto.setOperator("%" + searchLogVo.getOperator() + "%");
        }
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            if (StringUtils.isNotBlank(param.getFromTime())) {
//                dto.setFromTime(dateFormat.parse(param.getFromTime()));
//            }
//            if (StringUtils.isNotBlank(param.getToTime())) {
//                dto.setToTime(dateFormat.parse(param.getToTime()));
//            }
//        } catch (Exception e) {
//            throw new ParamException("传入的日期格式有问题，正确格式为：yyyy-MM-dd HH:mm:ss");
//        }
        PageHelper.startPage(page, size);
        List<SysLog> logList = sysLogMapper.getPageListBySearchDto(dto);
        PageInfo<SysLog> pageInfo = new PageInfo<>(logList);
        return new PageModel<>(logList, pageInfo);
    }

    /**
     * 还原操作
     *
     * @param id
     */
    @Override
    public void recover(String id) {
        SysLog sysLog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()) {
            case LogConst.TYPE_DEPT:
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (beforeDept == null) {
                    throw new NoResultException("待还原的部门已经不存在了");
                }
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new RecoverOPException("新增和删除操作不做还原");
                }
                SysDept afterDept = JSON.parseObject(sysLog.getOldValue(), SysDept.class);
                DeptVo deptParam = new DeptVo();
                deptParam.setId(afterDept.getId());
                deptParam.setName(afterDept.getName());
                deptParam.setParentId(afterDept.getParentId());
                deptParam.setSeq(afterDept.getSeq());
                deptParam.setRemark(afterDept.getRemark());
                // 这里涉及到部门树结构的更新，需要调用service层递归更新子级部门
                sysDeptService.update(deptParam);
                break;
            case LogConst.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (beforeUser == null) {
                    throw new NoResultException("待还原的用户已经不存在了");
                }
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new RecoverOPException("新增和删除操作不做还原");
                }
                SysUser afterUser = JSON.parseObject(sysLog.getOldValue(), SysUser.class);
                // 用户密码置空，不做更新
                afterUser.setPassword(null);
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperateTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser, afterUser);
                break;
            case LogConst.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (beforeAclModule == null) {
                    throw new NoResultException("待还原的权限模块已经不存在了");
                }
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new RecoverOPException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JSON.parseObject(sysLog.getOldValue(), SysAclModule.class);
//                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
//                afterAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//                afterAclModule.setOperateTime(new Date());
                AclModuleVo aclModuleParam = new AclModuleVo();
                aclModuleParam.setId(afterAclModule.getId());
                aclModuleParam.setName(afterAclModule.getName());
                aclModuleParam.setParentId(afterAclModule.getParentId());
                aclModuleParam.setRemark(afterAclModule.getRemark());
                aclModuleParam.setSeq(afterAclModule.getSeq());
                aclModuleParam.setStatus(afterAclModule.getStatus());
                aclModuleParam.setModuleUrl(aclModuleParam.getModuleUrl());
                // 这里涉及到权限模块树结构的更新，需要调用service层递归更新子级权限模块
                sysAclModuleService.update(aclModuleParam);
                break;
            case LogConst.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (beforeAcl == null) {
                    throw new NoResultException("待还原的权限点已经不存在了");
                }
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new RecoverOPException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JSON.parseObject(sysLog.getOldValue(), SysAcl.class);
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperateTime(new Date());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            case LogConst.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (beforeRole == null) {
                    throw new NoResultException("待还原的角色已经不存在了");
                }
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new RecoverOPException("新增和删除操作不做还原");
                }
                SysRole afterRole = JSON.parseObject(sysLog.getOldValue(), SysRole.class);
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperateTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            case LogConst.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (aclRole == null) {
                    throw new NoResultException("角色已经不存在了");
                }
                List<String> aclIdList = JSON.parseArray(sysLog.getOldValue(), String.class);
                sysRoleAclService.changeRoleAcls(sysLog.getTargetId(), aclIdList);
                break;
            case LogConst.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                if (userRole == null) {
                    throw new NoResultException("角色已经不存在了");
                }
                List<String> userIdList = JSON.parseArray(sysLog.getOldValue(), String.class);
                sysRoleUserService.changeRoleUsers(sysLog.getTargetId(), userIdList);
                break;
            default:
        }
        SysLog newLog = SysLog.builder().id(id).status(2).build();
        sysLogMapper.updateByPrimaryKeySelective(newLog);
    }
}
