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
@JSONType(orders = {"id", "name", "parentId", "fullLevel", "seq", "status", "remark", "moduleUrl", "operator", "operateIp", "operateTime"})
public class SysAclModule {

    private String id;

    private String name;

    private String parentId;

    private String fullLevel;

    private Integer seq;

    private Integer status;

    private String remark;

    private String moduleUrl;

    private String operator;

    private Date operateTime;

    private String operateIp;
}