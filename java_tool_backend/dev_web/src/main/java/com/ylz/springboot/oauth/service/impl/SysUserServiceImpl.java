package com.ylz.springboot.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ylz.springboot.commons.exception.*;
import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysDeptMapper;
import com.ylz.springboot.oauth.dao.SysRoleMapper;
import com.ylz.springboot.oauth.dao.SysRoleUserMapper;
import com.ylz.springboot.oauth.dao.SysUserMapper;
import com.ylz.springboot.oauth.pojo.SysDept;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.pojo.SysRoleUser;
import com.ylz.springboot.oauth.pojo.SysUser;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.service.SysUserService;
import com.ylz.springboot.oauth.vo.UserVo;
import com.ylz.springboot.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SysUserServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.17
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Value("${system.default.password}")
    private String defaultPassword;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    @Transactional(rollbackFor = AddOPException.class)
    public void save(UserVo userVo) {
        try {
            BeanValidatorUtil.check(userVo);
            if (checkTelephoneExist(userVo.getTelephone(), userVo.getId())) {
                throw new DuplicationException("该联系方式已被占用");
            }
            if (checkEmailExist(userVo.getMail(), userVo.getId())) {
                throw new DuplicationException("该邮箱已被占用");
            }

            String password = defaultPassword;
            String md5Password = MD5Util.encode(password);
            SysUser sysUser = SysUser.builder()
                    .id(KeyGenerateUtil.generateOracleId())
                    .username(userVo.getUsername()).password(md5Password)
                    .telephone(userVo.getTelephone()).mail(userVo.getMail())
                    .deptId(userVo.getDeptId())
                    .status(userVo.getStatus()).remark(userVo.getRemark())
                    .build();
            sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            sysUser.setOperateTime(new Date());

            // 构建角色用户信息
            List<String> roleIdList = StringSplitUtil.splitToListString(userVo.getRoleIds(), StringSplitUtil.SEPERATOR_COMMA);
            List<SysRoleUser> roleUserList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(roleIdList)) {
                roleIdList.forEach(id -> {
                    SysRoleUser sysRoleUser = SysRoleUser.builder()
                            .id(KeyGenerateUtil.generateOracleId())
                            .userId(sysUser.getId())
                            .roleId(id)
                            .operator(RequestHolder.getCurrentUser().getUsername())
                            .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                            .operateTime(new Date())
                            .build();
                    roleUserList.add(sysRoleUser);
                });
            }
            // 存储用户信息
            sysUserMapper.insertSelective(sysUser);
            // 存储角色信息
            sysRoleUserMapper.batchInsert(roleUserList);

            sysLogService.saveUserLog(null, sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddOPException("新增用户操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = UpdateOPException.class)
    public void update(UserVo userVo) {
        try {
            BeanValidatorUtil.check(userVo);
            if (checkTelephoneExist(userVo.getTelephone(), userVo.getId())) {
                throw new DuplicationException("该联系方式已被占用");
            }
            if (checkEmailExist(userVo.getMail(), userVo.getId())) {
                throw new DuplicationException("该邮箱已被占用");
            }
            SysUser before = sysUserMapper.selectByPrimaryKey(userVo.getId());
            if (before == null) {
                throw new NoResultException("待更新的用户不存在");
            }

            SysUser after = SysUser.builder()
                    .id(userVo.getId())
                    .username(userVo.getUsername())
                    .telephone(userVo.getTelephone()).mail(userVo.getMail())
                    .deptId(userVo.getDeptId()).status(userVo.getStatus())
                    .remark(userVo.getRemark()).build();
            after.setOperator(RequestHolder.getCurrentUser().getUsername());
            after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            after.setOperateTime(new Date());

            // 构建角色用户信息
            List<String> roleIdList = StringSplitUtil.splitToListString(userVo.getRoleIds(), StringSplitUtil.SEPERATOR_COMMA);
            List<SysRoleUser> roleUserList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(roleIdList)) {
                roleIdList.forEach(id -> {
                    SysRoleUser sysRoleUser = SysRoleUser.builder()
                            .id(KeyGenerateUtil.generateOracleId())
                            .userId(after.getId())
                            .roleId(id)
                            .operator(RequestHolder.getCurrentUser().getUsername())
                            .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                            .operateTime(new Date())
                            .build();
                    roleUserList.add(sysRoleUser);
                });
            }

            sysUserMapper.updateByPrimaryKeySelective(after);
            // 更新角色用户信息
            sysRoleUserMapper.deleteByUserId(after.getId());
            sysRoleUserMapper.batchInsert(roleUserList);

            sysLogService.saveUserLog(before, after);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateOPException("更新用户操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectAllUser();
    }

    @Override
    public PageModel<UserVo> findByPage(Integer page, Integer size, String deptId, String keyword) {
        PageHelper.startPage(page, size);
        // 部门ID为空时查询全部
        String searchKeyword = "%" + keyword + "%";
        List<SysUser> sysUsers = sysUserMapper.findByDeptId(deptId, searchKeyword);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        List<UserVo> userList = Lists.newArrayList();
        sysUsers.forEach(sysUser -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser, userVo);
            // 查询角色信息
            List<String> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(sysUser.getId());
            List<SysRole> roles = sysRoleMapper.findByIdList(roleIdList);
            // 查询用户所在部门的完整层级
            SysDept dept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
            if (dept != null) {
                userVo.setFullLevel(dept.getFullLevel());
            }
            userVo.setRoles(roles);
            userVo.setRoleIds(StringSplitUtil.stringListToString(roleIdList, StringSplitUtil.SEPERATOR_COMMA));
            userList.add(userVo);
        });
        return new PageModel<>(userList, pageInfo);
    }

    @Override
    public PageModel<UserVo> findByRoleName(Integer page, Integer size, String deptId, String roleName) {
        PageHelper.startPage(page, size);
        String searchRoleName = "%" + roleName + "%";
        List<SysRole> sysRoles = sysRoleMapper.findByKeyword(searchRoleName);
        System.out.println(sysRoles.size());
        Set<String> roleIdSet = sysRoles.stream().map(sysRole -> sysRole.getId()).collect(Collectors.toSet());

        List<SysUser> sysUsers = sysUserMapper.findByDeptId(deptId, null);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);

        List<UserVo> userList = Lists.newArrayList();
        sysUsers.forEach(sysUser -> {
            // 查询角色信息
            List<String> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(sysUser.getId());
            roleIdList.forEach(roleId -> {
                if (roleIdSet.contains(roleId)) {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(sysUser, userVo);
                    List<SysRole> roles = sysRoleMapper.findByIdList(Lists.newArrayList(roleId));
                    // 查询用户所在部门的完整层级
                    SysDept dept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
                    if (dept != null) {
                        userVo.setFullLevel(dept.getFullLevel());
                    }
                    userVo.setRoles(roles);
                    userVo.setRoleIds(StringSplitUtil.stringListToString(roleIdList, StringSplitUtil.SEPERATOR_COMMA));
                    userList.add(userVo);
                }
            });
        });
        return new PageModel<UserVo>(userList, pageInfo);
    }

    @Override
    @Transactional(rollbackFor = DeleteOPException.class)
    public void delete(String userId) {
        try {
            sysUserMapper.deleteByPrimaryKey(userId);
            sysRoleUserMapper.deleteByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeleteOPException("删除用户操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    public void validatePassword(UserVo userVo, String password) {
        String md5Password = MD5Util.encode(password);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userVo.getId());
        if (sysUser == null) {
            throw new NoResultException("用户不存在，请查看是否处于登录状态");
        }
        if (!sysUser.getPassword().equals(md5Password)) {
            throw new ModifyPasswordException("原密码不匹配，请重新输入");
        }
    }

    @Override
    public void changePassword(UserVo userVo, String password) {
        // 检验密码
        // validatePassword(userVo, password);
        String md5Password = MD5Util.encode(password);
        SysUser sysUser = SysUser.builder().id(userVo.getId()).password(md5Password).build();
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    private boolean checkTelephoneExist(String telephone, String id) {
        return sysUserMapper.countByTelephone(telephone, id) > 0;
    }


    private boolean checkEmailExist(String email, String id) {
        return sysUserMapper.countByEmail(email, id) > 0;
    }
}
