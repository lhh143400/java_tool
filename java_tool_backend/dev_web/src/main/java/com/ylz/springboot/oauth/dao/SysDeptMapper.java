package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> selectChildDeptByLevel(@Param("fullLevel") String fullLevel, @Param("parentId") String parentId);

    int countByParentId(@Param("deptId") String deptId);

    int countByNameAndParentId(@Param("parentId") String parentId, @Param("name") String name, @Param("id") String id);

    List<SysDept> selectAllDept();
}