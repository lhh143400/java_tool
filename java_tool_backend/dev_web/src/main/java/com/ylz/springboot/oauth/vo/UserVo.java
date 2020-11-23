package com.ylz.springboot.oauth.vo;

import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysRole;
import com.ylz.springboot.utils.MD5Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * UserVo
 *
 * @author: Chris
 * @time: 2019.02.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private String id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 1, max = 20, message = "用户名长度需要在20个字以内")
    private String username;

    @NotBlank(message = "联系方式不能为空")
    @Length(max = 13, message = "电号码长度不能超过13个字符")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    private String mail;

    @NotNull(message = "必须提供用户所在的部门")
    private String deptId;

    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(max = 150, message = "备注信息长度不能超过150个字")
    private String remark = "";

    @NotBlank(message = "角色不能为空")
    private String roleIds;

    private List<SysRole> roles;

    private String fullLevel;

    private List<SysAcl> acls;

}
