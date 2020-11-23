package com.ylz.springboot.modules.elasticsearch.dao;

import com.ylz.springboot.modules.elasticsearch.pojo.SynthesizeCorpus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lhh
 * @Date 2019/9/24 20:58
 */
@Repository
public interface CorpusEsRepository  extends ElasticsearchRepository<SynthesizeCorpus,String> {
}
