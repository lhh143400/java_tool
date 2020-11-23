package com.ylz.springboot.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ylz.springboot.commons.exception.DeleteOPException;
import com.ylz.springboot.commons.exception.DuplicationException;
import com.ylz.springboot.commons.exception.NoResultException;
import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.common.AclConst;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysAclMapper;
import com.ylz.springboot.oauth.dao.SysAclModuleMapper;
import com.ylz.springboot.oauth.dao.SysRoleAclMapper;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysAclModule;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.service.SysAclService;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.vo.AclVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SysAclServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysAclServiceImpl implements SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Override
    public void save(AclVo aclVo) {
        BeanValidatorUtil.check(aclVo);
        if (checkExist(aclVo.getAclModuleId(), aclVo.getName(), aclVo.getId())) {
            throw new DuplicationException("当前权限模块下面存在相同名称的权限点");
        }
        if (checkUrlExist(aclVo.getUrl(), aclVo.getId())) {
            throw new DuplicationException("当前URL已被使用");
        }
        SysAcl acl = SysAcl.builder()
                .id(KeyGenerateUtil.generateOracleId())
                .name(aclVo.getName()).aclModuleId(aclVo.getAclModuleId())
                .url(aclVo.getUrl()).type(aclVo.getType())
                .status(aclVo.getStatus()).seq(aclVo.getSeq())
                .remark(aclVo.getRemark())
                .build();
        acl.setCode(generateAclCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());

        sysAclMapper.insertSelective(acl);
        sysLogService.saveAclLog(null, acl);
    }

    @Override
    public void update(AclVo aclVo) {
        BeanValidatorUtil.check(aclVo);
        if (checkExist(aclVo.getAclModuleId(), aclVo.getName(), aclVo.getId())) {
            throw new DuplicationException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(aclVo.getId());
        if (before == null) {
            throw new NoResultException("待更新的权限点不存在");
        }
        if (checkUrlExist(aclVo.getUrl(), aclVo.getId())) {
            throw new DuplicationException("当前URL已被使用");
        }
        SysAcl after = SysAcl.builder().id(aclVo.getId()).name(aclVo.getName()).aclModuleId(aclVo.getAclModuleId()).url(aclVo.getUrl())
                .type(aclVo.getType()).status(aclVo.getStatus()).seq(aclVo.getSeq()).remark(aclVo.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after);
    }

    @Override
    public PageModel<AclVo> findByPage(Integer page, Integer size, String aclModuleId, String keyword) {
        PageHelper.startPage(page, size);
        // 权限模块ID为空时查询全部
        String searchKeyword = "%" + keyword + "%";
        List<SysAcl> sysAcls = sysAclMapper.findByAclModuleIdAndKeyword(aclModuleId, searchKeyword);
        PageInfo<SysAcl> pageInfo = new PageInfo<>(sysAcls);

        List<AclVo> aclList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(sysAcls)) {
            sysAcls.forEach(sysAcl -> {
                AclVo aclVo = new AclVo();
                BeanUtils.copyProperties(sysAcl, aclVo);
                SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(sysAcl.getAclModuleId());
                aclVo.setFullLevel(sysAclModule != null ? sysAclModule.getFullLevel() : "");
                aclList.add(aclVo);
            });
        }

        return new PageModel<AclVo>(aclList, pageInfo);
    }

    private boolean checkExist(String aclModuleId, String name, String id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    private boolean checkUrlExist(String url, String id) {
        return sysAclMapper.countByUrl(url, id) > 0;
    }

    public static String generateAclCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 1000);
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new DeleteOPException("权限点ID不能为空");
        }
        SysAcl sysAcl = sysAclMapper.selectByPrimaryKey(id);
        if (sysAcl == null) {
            throw new NoResultException("待删除的权限点不存在");
        }
        if (AclConst.ACL_CODE_DEFAULT.equals(sysAcl.getCode())) {
            throw new DeleteOPException("默认权限点无法删除");
        }
        sysAclMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysAcl> findAclsByUserAndModuleUrl(List<SysRole> roles, String moduleUrl) {
        return null;
    }
}
