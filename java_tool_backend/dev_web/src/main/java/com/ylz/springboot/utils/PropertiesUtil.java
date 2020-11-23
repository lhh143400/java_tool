package com.ylz.springboot.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Properties 配置信息 文件读取工具类
 *
 * @author lhh
 * @date 2019/9/9
 */
public class PropertiesUtil {
    private static Properties props = new Properties();

    static {
        Properties base = new Properties();
        try {
            base.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"), StandardCharsets.UTF_8));
            String active = base.getProperty("spring.profiles.active");
            switch (active) {
                case "dev":
                    props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("application-dev.properties"), StandardCharsets.UTF_8));
                    break;
                case "test":
                    props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("application-test.properties"), StandardCharsets.UTF_8));
                    break;
                case "prod":
                    props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("application-prod.properties"), StandardCharsets.UTF_8));
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return props.getProperty(key);
    }
    /**
     * 测试例子
     */
    public void test(){
        //1.注解上使用
        //类上要被spring 管理,记得价格@Component
       /* @Value("${backend.url-prefix}")
        private String imageUrlPrefix;*/
       //2.直接调用
        PropertiesUtil.getString("server.port");
    }
}
