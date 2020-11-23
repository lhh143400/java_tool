package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByNameAndAclModuleId(@Param("aclModuleId") String aclModuleId, @Param("name") String name, @Param("id") String id);

    int countByAclModuleId(@Param("aclModuleId") String aclModuleId);

    int countByAclModuleIdAndCode(@Param("aclModuleId") String aclModuleId, @Param("code") String code);

    List<SysAcl> findByAclModuleIdAndKeyword(@Param("aclModuleId") String aclModuleId, @Param("keyword") String keyword);

    SysAcl findByAclModuleIdAndTypeAndCode(@Param("aclModuleId") String aclModuleId, @Param("type") Integer type,
                                           @Param("code") String code);

    List<SysAcl> findByAclIdList(@Param("aclIdList") List<String> aclIdList);

    List<SysAcl> selectAllAcl();

    int deleteByAclModuleIdAndCode(@Param("aclModuleId") String aclModuleId, @Param("code") String code);

    int countByUrl(@Param("url") String url, @Param("id") String id);
}