package com.ylz.springboot.oauth.service;

import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.pojo.SysUser;
import com.ylz.springboot.oauth.vo.UserVo;

import java.util.List;

/**
 * SysUserService
 *
 * @author: Chris
 * @time: 2019.02.17
 */
public interface SysUserService {

    void save(UserVo userVo);

    void update(UserVo userVo);

    SysUser findByKeyword(String keywork);

    List<SysUser> findAll();

    PageModel<UserVo> findByPage(Integer page, Integer size, String deptId, String keyword);

    void delete(String userId);

    void validatePassword(UserVo userVo, String password);

    void changePassword(UserVo userVo, String password);

    PageModel<UserVo> findByRoleName(Integer page, Integer size, String deptId, String roleName);

}
