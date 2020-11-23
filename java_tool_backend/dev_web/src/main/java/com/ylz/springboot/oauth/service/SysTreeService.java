package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.dto.AclModuleLevelDto;
import com.ylz.springboot.oauth.dto.DeptLevelDto;

import java.util.List;

/**
 * SysTreeService
 *
 * @author: Chris
 * @time: 2019.02.15
 */
public interface SysTreeService {

    List<DeptLevelDto> deptTree();

    List<AclModuleLevelDto> aclModuleTree();

    List<AclModuleLevelDto> roleTree(String roleId);

    List<AclModuleLevelDto> functionTree();
}
