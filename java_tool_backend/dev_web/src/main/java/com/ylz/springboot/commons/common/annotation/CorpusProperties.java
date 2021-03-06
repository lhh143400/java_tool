package com.ylz.springboot.commons.common.annotation;

import java.lang.annotation.*;

/**
 * 语料数据注解
 *
 * @author lhh
 * @date 2019/9/18
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CorpusProperties{


    /**
     * 问题
     */
    String cn() default "";

    /**
     * 分组
     */
    int group() default 1;

    /**
     * 排序
     */
    int order() default 1;

    /**
     * 是否头部
     */
    boolean header() default false;

    /**
     * 计算公式
     */
    String calcFormula() default "";

    /**
     * echart 是否显示
     */
    boolean checked() default false;
}
