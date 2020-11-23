package com.ylz.springboot.oauth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * DeptVo
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptVo {

    private String id;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 20, message = "部门名称长度不能超过20个字")
    private String name;

    private String parentId = "0";

    @NotNull(message = "部门排序编号不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注信息长度不能超过150个字")
    private String remark;

}
