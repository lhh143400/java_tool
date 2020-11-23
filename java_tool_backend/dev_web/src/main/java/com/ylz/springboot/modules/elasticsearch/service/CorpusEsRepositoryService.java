package com.ylz.springboot.modules.elasticsearch.service;

import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import com.ylz.springboot.modules.elasticsearch.pojo.SynthesizeCorpus;

import java.util.List;

/**
 * @author lhh
 * @Date 2019/9/24 20:50
 */
public interface CorpusEsRepositoryService {

    /**
     * 批量新增
     * @param list 书籍对象
     */
     void batchSave(List<SynthesizeCorpus> list);


    /**
     * 单条新增
     * @param obj 书籍对象
     */
     void save(SynthesizeCorpus obj);


    /**
     * 分词搜索 author
     *
     * @param  question 问题
     * @return 对象
     */
    List<SynthesizeCorpus> getByQuestion(String question);
}
