package com.ylz.springboot.commons.utils.execl;

import com.ylz.springboot.commons.utils.poi.ExcelPoiProperty;
import lombok.Data;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 11:33
 * Description: 读取Excel时，封装读取的每一行的数据
 */
@Data
public class ExcelDataVo {

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
