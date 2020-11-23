package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<String> getUserIdListByRoleId(@Param("roleId") String roleId);

    void deleteByRoleId(@Param("roleId") String roleId);

    void deleteByUserId(@Param("userId") String userId);

    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

    List<String> getRoleIdListByUserId(@Param("userId") String userId);
}