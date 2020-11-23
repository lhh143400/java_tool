package com.ylz.springboot.modules.elasticsearch.service.impl;

import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import com.ylz.springboot.modules.elasticsearch.service.EsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lhh
 * @Date 2019/9/14 17:45
 */
@Service
public class EsTemplateServiceImpl implements EsTemplateService{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public void save(ArticleBook articleBook) {
    }
}
