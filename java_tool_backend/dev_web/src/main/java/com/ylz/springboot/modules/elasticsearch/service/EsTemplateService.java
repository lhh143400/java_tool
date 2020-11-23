package com.ylz.springboot.modules.elasticsearch.service;

import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;

/**
 * es template
 *
 * @author lhh
 * @Date 2019/9/14 17:45
 */
public interface EsTemplateService {


    void save(ArticleBook articleBook);
}
