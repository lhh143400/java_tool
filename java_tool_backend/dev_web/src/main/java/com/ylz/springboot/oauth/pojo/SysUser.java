package com.ylz.springboot.oauth.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.*;

/**
 * Created by Mybatis Generator 2019/02/14
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JSONType(orders = {"id", "username", "password", "mail", "telephone", "deptId", "status", "remark", "operator", "operateIp", "operateTime"})
public class SysUser {

    private String id;

    private String username;

    private String password;

    private String mail;

    private String telephone;

    private String deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}