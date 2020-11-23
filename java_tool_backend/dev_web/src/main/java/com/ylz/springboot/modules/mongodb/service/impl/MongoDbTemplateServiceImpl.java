package com.ylz.springboot.modules.mongodb.service.impl;

import com.ylz.springboot.modules.mongodb.pojo.MongoTemplateBook;
import com.ylz.springboot.modules.mongodb.service.MongoDbTemplateService;
import com.ylz.springboot.modules.mongodb.vo.MongoJpaBookVo;
import com.ylz.springboot.modules.mongodb.vo.MongoTemplateBookVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author lhh
 * @Date 2019/9/13 17:23
 */
@Service("MongoDbTemplateService")
public class MongoDbTemplateServiceImpl implements MongoDbTemplateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     *
     * @param vo 视图层书本对象
     */
    @Override
    public void save(MongoTemplateBookVo vo) {
        BeanValidatorUtil.check(vo);
        MongoTemplateBook book=new MongoTemplateBook();
        BeanUtils.copyProperties(vo,book);
        book.setId(KeyGenerateUtil.generateOracleId());
        mongoTemplate.insert(vo);
    }

    /**
     * 创建集合
     *
     * @param colName 集合名称
     */
    @Override
    public void createCollection(String colName) {

    }

    /**
     * 修改
     *
     * @param vo 视图层书本对象
     */
    @Override
    public void update(MongoTemplateBookVo vo) {

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {

    }
}
