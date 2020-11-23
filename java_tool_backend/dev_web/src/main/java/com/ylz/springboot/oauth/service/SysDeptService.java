package com.ylz.springboot.oauth.service;

import com.ylz.springboot.oauth.vo.DeptVo;

/**
 * SysDeptService
 *
 * @author: Chris
 * @time: 2019.02.15
 */
public interface SysDeptService {

    void save(DeptVo deptVo);

    void update(DeptVo deptVo);

    void delete(String id);

}
