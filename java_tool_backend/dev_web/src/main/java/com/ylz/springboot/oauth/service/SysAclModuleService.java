package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.vo.AclModuleVo;

/**
 * SysAclModuleService
 *
 * @author: Chris
 * @time: 2019.02.18
 */
public interface SysAclModuleService {

    void save(AclModuleVo aclModuleVo);

    void update(AclModuleVo aclModuleVo);

    void delete(String aclModuleId);

}
