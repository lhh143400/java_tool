package com.ylz.springboot.oauth.vo;

import lombok.Data;

/**
 * SearchLogVo
 *
 * @author: Chris
 * @time: 2019.02.19
 */
@Data
public class SearchLogVo {

    private Integer type;

    private String oldValue;

    private String newValue;

    private String operator;

//    private String fromTime;

//    private String toTime;

}
