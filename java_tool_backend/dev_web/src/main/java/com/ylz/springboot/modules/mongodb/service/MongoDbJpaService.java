package com.ylz.springboot.modules.mongodb.service;

import com.ylz.springboot.modules.mongodb.vo.MongoJpaBookVo;

/**
 *
 *
 * @author lhh
 * @date 2019/9/13
 */
public interface MongoDbJpaService {

    /**
     * 保存
     *
     * @param vo 视图层书本对象
     */
    void save(MongoJpaBookVo vo);

    /**
     * 创建集合
     *
     * @param colName 集合名称
     */
    void createCollection(String colName);

    /**
     * 修改
     *
     * @param vo 视图层书本对象
     */
    void update(MongoJpaBookVo vo);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

}
