package com.ylz.springboot.modules.elasticsearch.service.impl;

import com.ylz.springboot.modules.elasticsearch.dao.EsRepository;
import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import com.ylz.springboot.modules.elasticsearch.service.EsRepositoryService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhh
 * @Date 2019/9/16 23:12
 */
@Service("EsRepositoryService")
public class EsRepositoryServiceImpl implements EsRepositoryService {

    @Autowired
    private EsRepository esRepository;



    /**
     * 批量导入 es
     */
    @Override
    public void batchSave(List<ArticleBook> list){
        try {
            esRepository.saveAll(list);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 单条新增
     *
     * @param obj 书籍对象
     */
    @Override
    public void save(ArticleBook obj) {
         esRepository.save(obj);
    }

    /**
     * 批量删除
     *
     */
    @Override
    public void batchDelete(){
        esRepository.deleteAll();
    }

    /**
     * 单条删除
     *
     * @param id 指定id 删除
     */
    @Override
    public void delId(String id){
        esRepository.deleteById(id);
    }

    @Override
    public long count() {
        return esRepository.count();
    }



    @Override
    public void delete(ArticleBook commodity) {
        esRepository.delete(commodity);
//        commodityRepository.deleteById(commodity.getSkuId());
    }

    @Override
    public Iterable<ArticleBook> getAll() {
        return esRepository.findAll();
    }

    @Override
    public List<ArticleBook> getByBookName(String name) {
        List<ArticleBook> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("author", name);
        Iterable<ArticleBook> iterable = esRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @Override
    public Page<ArticleBook> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("author", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return esRepository.search(searchQuery);
    }
}
