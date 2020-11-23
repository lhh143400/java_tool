package com.ylz.springboot.config.converter;

/**
 * Json解析配置类
 * 如果需要使用fastjson解析器则打开configuration注解即可
 *
 * @author: Chris
 * @time: 2018.08.20
 */
//@Configuration
public class ConverterConfig {

    /**
     * 使用自定义第三方json框架（fastjson）进行json数据转换
     *
     * @return
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
//        //处理中文乱码问题
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        converter.setSupportedMediaTypes(fastMediaTypes);
//        converter.setFastJsonConfig(config);
//        HttpMessageConverter<?> httpMessageConverter = converter;
//        return new HttpMessageConverters(httpMessageConverter);
//    }

    /**
     * gson
     * @param gson
     * @return
     */
//    @Bean
//    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
//        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//        converter.setGson(gson);
//        return converter;
//    }
}
