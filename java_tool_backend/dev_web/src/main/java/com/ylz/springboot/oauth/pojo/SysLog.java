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
public class SysLog {

    private String id;

    private Integer type;

    private String targetId;

    private String oldValue;

    private String newValue;

    private String operator;

    private Date operateTime;

    private String operateIp;

    private Integer status;
}