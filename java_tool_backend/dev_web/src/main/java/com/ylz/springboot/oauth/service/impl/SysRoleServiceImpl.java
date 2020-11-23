package com.ylz.springboot.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylz.springboot.commons.exception.DeleteOPException;
import com.ylz.springboot.commons.exception.DuplicationException;
import com.ylz.springboot.commons.exception.NoResultException;
import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysRoleAclMapper;
import com.ylz.springboot.oauth.dao.SysRoleMapper;
import com.ylz.springboot.oauth.dao.SysRoleUserMapper;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.service.SysRoleService;
import com.ylz.springboot.oauth.vo.RoleVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SysRoleServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Override
    public void save(RoleVo roleVo) {
        BeanValidatorUtil.check(roleVo);
        if (checkExist(roleVo.getName(), roleVo.getId())) {
            throw new DuplicationException("角色名称已存在");
        }
        SysRole role = SysRole.builder()
                .id(KeyGenerateUtil.generateOracleId())
                .name(roleVo.getName())
                .status(roleVo.getStatus()).type(roleVo.getType())
                .remark(roleVo.getRemark()).build();
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
        sysLogService.saveRoleLog(null, role);
    }

    @Override
    public void update(RoleVo roleVo) {
        BeanValidatorUtil.check(roleVo);
        if (checkExist(roleVo.getName(), roleVo.getId())) {
            throw new DuplicationException("角色名称已存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(roleVo.getId());
        if (before == null) {
            throw new NoResultException("待更新用户不存在");
        }
        SysRole after = SysRole.builder()
                .id(roleVo.getId())
                .name(roleVo.getName())
                .status(roleVo.getStatus()).type(roleVo.getType())
                .remark(roleVo.getRemark())
                .build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveRoleLog(before, after);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    public PageModel<SysRole> findByPage(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);
        String searchKeyword = "%" + keyword + "%";
        List<SysRole> sysRoles = sysRoleMapper.findByKeyword(searchKeyword);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);
        return new PageModel<SysRole>(sysRoles, pageInfo);
    }

    @Override
    public void delete(String id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            throw new DeleteOPException("待删除的角色不存在");
        }
        if (sysRoleUserMapper.getUserIdListByRoleId(id).size() > 0) {
            throw new DeleteOPException("该角色已被用户使用，请到用户管理中修改用户与角色的关联关系，或者删除用户");
        }
        if (sysRoleAclMapper.findAclIdListByRoleId(id).size() > 0) {
            throw new DeleteOPException("该角色下有关联的权限点，无法删除");
        }
        sysRoleMapper.deleteByPrimaryKey(id);
    }

    private boolean checkExist(String name, String id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }
}
