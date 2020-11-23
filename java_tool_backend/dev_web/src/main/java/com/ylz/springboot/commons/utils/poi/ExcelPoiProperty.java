package com.ylz.springboot.commons.utils.poi;

import java.lang.annotation.*;

/**
 * execl 列 bean
 *
 * @author lhh
 * @Date 2019/11/6 1:05
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelPoiProperty {

    String value() default "";

    int index() default 99999;
}
