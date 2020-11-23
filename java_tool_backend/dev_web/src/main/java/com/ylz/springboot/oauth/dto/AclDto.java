package com.ylz.springboot.oauth.dto;

import com.ylz.springboot.oauth.pojo.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * AclDto
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Data
public class AclDto extends SysAcl {

    /**
     * 是否要默认选中
     */
    private boolean checked = false;

    /**
     * 用户是否有权限操作该权限模块和权限点
     */
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }

}
