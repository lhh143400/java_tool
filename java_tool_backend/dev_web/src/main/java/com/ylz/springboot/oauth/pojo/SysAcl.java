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
@JSONType(orders = {"id", "code", "name", "aclModuleId", "url", "type", "status", "seq", "remark", "operator", "operateIp", "operateTime"})
public class SysAcl {

    private String id;

    private String code;

    private String name;

    private String aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}