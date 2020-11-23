package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.dto.AclModuleLevelDto;
import com.ylz.springboot.oauth.pojo.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    List<SysAclModule> selectChildAclModuleByLevel(@Param("fullLevel") String fullLevel, @Param("parentId") String parentId);

    int countByParentId(@Param("aclModuleId") String aclModuleId);

    int countByNameAndParentId(@Param("parentId") String parentId, @Param("name") String name, @Param("id") String id);

    List<SysAclModule> selectAllAclModule();

    List<AclModuleLevelDto> findByAclModuleIdList(@Param("idList") List<String> aclModuleIdList);

    SysAclModule selectByModuleUrl(@Param("moduleUrl") String moduleUrl);
}