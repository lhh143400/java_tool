package com.ylz.springboot.commons.common.annotation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * 指标数据输出格式
 *
 * @author wangsk
 * @date 2019/9/18
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexOutput {

    /**
     * 指标数据值
     */
    private Object value;
    /**
     * 英文名称
     */
    private String en;
    /**
     * 中文名称
     */
    private String cn;
    /**
     * 分组
     */
    private int group;
    /**
     * 排序
     */
    private int order;
    /**
     * 是否头部
     */
    private boolean header;
    /**
     * 计算公式
     */
    private String calcFormula;

    /**
     * echart 是否显示
     */
    private boolean checked;

    /**
     * 格式化
     *
     * @param obj 待格式化对象
     * @return 格式化后的对象
     */
    public static Collection<List<IndexOutput>> cast(Object obj) throws IllegalAccessException {
        TreeMap<Integer, List<IndexOutput>> treeMap = Maps.newTreeMap();

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //获取类里的属性
        for (Field field : fields) {
            //是否使用IndexProperties注解
            if (field.isAnnotationPresent(IndexProperties.class)) {
                //获取注解里的值
                IndexProperties indexProperties = field.getAnnotation(IndexProperties.class);
                //作用就是让我们在用反射时访问私有变量
                field.setAccessible(true);
                //分组
                if (!treeMap.containsKey(indexProperties.group())) {
                    treeMap.put(indexProperties.group(), Lists.newArrayList());
                }
                treeMap.get(indexProperties.group()).add(
                        IndexOutput.builder().value(field.get(obj)).en(indexProperties.en()).cn(indexProperties.cn())
                                .group(indexProperties.group()).order(indexProperties.order()).checked(indexProperties.checked())
                                .header(indexProperties.header()).calcFormula(indexProperties.calcFormula()).build()
                );
            }
        }

        Collection<List<IndexOutput>> result = treeMap.values();
        for (List<IndexOutput> list : result) {
            list.sort((a, b) -> a.getOrder() - b.getOrder());
        }
        return result;
    }

    /**
     *  格式化 二维 转 一维
     *
     * @param obj 待格式化对象
     * @return 格式化后的对象
     */
    public static List<IndexOutput> castNoGroup(Object obj) throws IllegalAccessException {
        List<IndexOutput> outputList=Lists.newArrayList();
        Collection<List<IndexOutput>> cast = cast(obj);
        for (List<IndexOutput> list:cast){
            for (IndexOutput indexOutput:list){
                outputList.add(indexOutput);
            }
        }
        return outputList;
    }
}
