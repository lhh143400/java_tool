package com.ylz.springboot.oauth.service;

import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.vo.RoleVo;

import java.util.List;

/**
 * SysRoleService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysRoleService {

    void save(RoleVo roleVo);

    void update(RoleVo roleVo);

    List<SysRole> findAll();

    PageModel<SysRole> findByPage(Integer page, Integer size, String keyword);

    void delete(String id);
}
