package com.ylz.springboot.modules.mongodb.service.impl;

import com.ylz.springboot.modules.mongodb.dao.BookDaoJpa;
import com.ylz.springboot.modules.mongodb.pojo.MongoJpaBook;
import com.ylz.springboot.modules.mongodb.service.MongoDbJpaService;
import com.ylz.springboot.modules.mongodb.vo.MongoJpaBookVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @Date 2019/9/13 17:19
 * @author lhh
 */
@Service("MongoDbJpaService")
public class MongoDbJpaServiceImpl implements MongoDbJpaService {

    @Autowired
    private BookDaoJpa bookDaoJpa;


    /**
     * 保存
     *
     * @param vo 视图层书本对象
     */
    @Override
    public void save(MongoJpaBookVo vo) {
        //检查入参格式是否正确性
        BeanValidatorUtil.check(vo);
        MongoJpaBook book=new MongoJpaBook();
        BeanUtils.copyProperties(vo,book);
        book.setId(KeyGenerateUtil.generateOracleId());
        bookDaoJpa.insert(book);
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
    public void update(MongoJpaBookVo vo) {

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
