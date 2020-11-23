package com.ylz.springboot.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * StringSplitUtil
 * 字符串切分工具类
 *
 * @author: Chris
 * @time: 2019.02.15
 */
public class StringSplitUtil {

    public static final String SEPERATOR_COMMA = ",";

    public static List<Integer> splitToListInt(String str, String seperator) {
        List<String> strList = Splitter.on(seperator).trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }

    public static List<String> splitToListString(String str, String split) {
        return Splitter.on(split).trimResults().omitEmptyStrings().splitToList(str);
    }

    public static String stringListToString(List<String> list, String seperator) {
        StringBuffer sb = new StringBuffer();
        list.forEach(item -> {
            sb.append(item).append(seperator);
        });
        return sb.toString();
    }

}
