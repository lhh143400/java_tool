package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator 2019/02/14
 */
public interface SysUserMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysUser entity);

    int insertSelective(SysUser entity);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser entity);

    int updateByPrimaryKey(SysUser entity);

    List<SysUser> selectAllUser();

    int countByTelephone(@Param("telephone") String telephone, @Param("id") String id);

    int countByEmail(@Param("email") String email, @Param("id") String id);

    int countByDeptId(@Param("deptId") String deptId);

    List<SysUser> findByDeptId(@Param("deptId") String deptId, @Param("keyword") String keyword);

    List<SysUser> findByIdList(@Param("idList") List<String> idList);

    SysUser findByKeyword(@Param("keyword") String keyword);

}