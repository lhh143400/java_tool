package com.ylz.springboot.oauth.service;

import com.ylz.springboot.commons.mapper.PageModel;
import com.ylz.springboot.oauth.pojo.*;
import com.ylz.springboot.oauth.vo.SearchLogVo;

import java.util.List;

/**
 * SysLogService
 *
 * @author: Chris
 * @time: 2019.02.19
 */
public interface SysLogService {

    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);

    void saveRoleAclLog(String roleId, List<String> before, List<String> after);

    void saveRoleUserLog(String roleId, List<String> before, List<String> after);

    PageModel<SysLog> searchPageList(Integer page, Integer size, SearchLogVo searchLogVo);

    void recover(String id);
}
