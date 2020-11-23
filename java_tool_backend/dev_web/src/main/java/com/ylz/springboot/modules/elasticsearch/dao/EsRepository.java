package com.ylz.springboot.modules.elasticsearch.dao;

import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * es jpa 形式
 *
 * @author lhh
 * @Date 2019/9/16 22:58
 */
@Repository
public interface EsRepository extends ElasticsearchRepository<ArticleBook,String> {
}
