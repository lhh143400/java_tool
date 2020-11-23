package com.ylz.springboot.oauth.dao;

import com.ylz.springboot.oauth.pojo.SysLog;
import com.ylz.springboot.oauth.vo.SearchLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> getPageListBySearchDto(@Param("dto") SearchLogVo dto);
}