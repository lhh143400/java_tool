package com.ylz.springboot.modules.mongodb.dao;

import com.ylz.springboot.modules.mongodb.pojo.MongoJpaBook;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * mongo jpa 书籍
 *
 * @author lhh
 * @Date 2019/9/13 17:30
 */
public interface BookDaoJpa extends MongoRepository<MongoJpaBook,String>{

}
