package com.ylz.springboot.commons.utils.poi;

import lombok.Data;

/**
 * 读取Excel时，封装读取的每一行的数据
 *
 * @author lhh
 * @Date 2019/11/13 1:04
 */
@Data
public class PoiExeclModel {

    /**
     * 姓名
     */
    @ExcelPoiProperty(value = "姓名",index = 0)
    private String name;

    /**
     * 年龄
     */
    @ExcelPoiProperty(value = "年龄",index = 1)
    private Integer age;

    /**
     * 居住地
     */
    @ExcelPoiProperty(value = "居住城市",index = 2)
    private String location;

    /**
     * 职业
     */
    @ExcelPoiProperty(value = "职业",index = 3)
    private String job;

}
