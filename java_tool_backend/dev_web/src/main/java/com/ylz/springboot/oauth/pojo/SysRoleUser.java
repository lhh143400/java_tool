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
public class SysRoleUser {

    private String id;

    private String roleId;

    private String userId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}