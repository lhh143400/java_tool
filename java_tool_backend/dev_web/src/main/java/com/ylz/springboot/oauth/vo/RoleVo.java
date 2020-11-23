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
 * RoleVo
 *
 * @author: Chris
 * @time: 2019.02.18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

    private String id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(max = 20, message = "角色名称长度不能超过20个字符")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Length(max = 150, message = "角色备注长度需要在150个字符以内")
    private String remark;

}
