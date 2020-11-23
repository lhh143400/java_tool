package com.ylz.springboot.oauth.pojo;

import java.util.Date;

import lombok.*;

/**
* Created by Mybatis Generator 2019/02/14
*/
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleAcl {

    private String id;

    private String roleId;

    private String aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}