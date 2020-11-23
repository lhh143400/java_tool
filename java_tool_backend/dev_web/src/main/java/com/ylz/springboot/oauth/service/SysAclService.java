package com.ylz.springboot.oauth.service;

import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.oauth.vo.AclVo;

import java.util.List;

/**
 * SysAclService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysAclService {

    void save(AclVo aclVo);

    void update(AclVo aclVo);

    PageModel<AclVo> findByPage(Integer page, Integer size, String aclModuleId, String keyword);

    void delete(String id);

    List<SysAcl> findAclsByUserAndModuleUrl(List<SysRole> roles, String moduleUrl);
}
