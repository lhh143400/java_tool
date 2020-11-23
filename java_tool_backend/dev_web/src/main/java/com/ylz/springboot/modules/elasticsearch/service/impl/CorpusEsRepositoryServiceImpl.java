package com.ylz.springboot.modules.elasticsearch.service.impl;

import com.ylz.springboot.modules.elasticsearch.dao.CorpusEsRepository;
import com.ylz.springboot.modules.elasticsearch.pojo.SynthesizeCorpus;
import com.ylz.springboot.modules.elasticsearch.service.CorpusEsRepositoryService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhh
 * @Date 2019/9/24 20:50
 */
@Service("CorpusEsRepositoryService")
public class CorpusEsRepositoryServiceImpl implements CorpusEsRepositoryService {
    @Autowired
    private CorpusEsRepository corpusEsRepository;


    /**
     * 批量新增
     *
     * @param list 书籍对象
     */
    @Override
    public void batchSave(List<SynthesizeCorpus> list) {
        corpusEsRepository.saveAll(list);
    }

    /**
     * 单条新增
     *
     * @param obj 书籍对象
     */
    @Override
    public void save(SynthesizeCorpus obj) {
        corpusEsRepository.save(obj);
    }

    /**
     * 分词搜索 author
     *
     * @param question 问题
     * @return 对象
     */
    @Override
    public List<SynthesizeCorpus> getByQuestion(String question) {
        List<SynthesizeCorpus> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("question", question);
        Iterable<SynthesizeCorpus> iterable = corpusEsRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }
}
