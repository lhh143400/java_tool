package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int countByName(@Param("name") String name, @Param("id") String id);

    List<SysRole> findAll();

    List<SysRole> findByIdList(@Param("roleIdList") List<String> roleIdList);

    List<SysRole> findByKeyword(@Param("keyword") String keyword);

}