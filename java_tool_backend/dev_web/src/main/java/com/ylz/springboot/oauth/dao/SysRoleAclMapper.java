package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface SysRoleAclMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    void deleteByRoleId(@Param("roleId") String roleId);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<String> findAclIdListByRoleId(@Param("roleId") String roleId);

}