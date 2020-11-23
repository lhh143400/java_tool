package com.ylz.springboot.oauth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * AclVo
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Data
public class AclVo {

    private String id;

    @NotBlank(message = "权限点名称不可以为空")
    @Length(max = 20, message = "权限点名称长度不能超过20个字符")
    private String name;

    @NotBlank(message = "必须指定归属的权限模块")
    private String aclModuleId;

    @Length(max = 100, message = "权限点URL长度不能超过100个字符")
    private String url;

    private String code;

    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1, message = "权限点类型不合法")
    @Max(value = 3, message = "权限点类型不合法")
    private Integer type;

    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0, message = "权限点状态不合法")
    @Max(value = 1, message = "权限点状态不合法")
    private Integer status;

    @NotNull(message = "必须指定权限点的展示顺序")
    private Integer seq;

    @Length(max = 150, message = "权限点备注信息长度需要在150个字符以内")
    private String remark;

    private String fullLevel;

}
