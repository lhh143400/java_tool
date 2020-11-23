package com.ylz.springboot.oauth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * AclModuleVo
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AclModuleVo {

    private String id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(max = 20, message = "权限模块名称长度不能超过20个字符")
    private String name;

    private String parentId = "0";

    @NotNull(message = "权限模块展示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    @Length(max = 150, message = "权限模块备注信息需要在150个字之间")
    private String remark;

    @Length(max = 200, message = "权限模块URL长度不能超过200个字符")
    private String moduleUrl;
}
