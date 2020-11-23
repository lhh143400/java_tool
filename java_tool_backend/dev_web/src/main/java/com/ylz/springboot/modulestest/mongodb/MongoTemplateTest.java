package com.ylz.springboot.modulestest.mongodb;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.modules.mongodb.service.MongoDbJpaService;
import com.ylz.springboot.modules.mongodb.vo.MongoJpaBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mongodb template 测试类
 *
 * @author lhh
 * @Date 2019/9/13 17:50
 */
@RestController
@RequestMapping("/mongo_temp_test")
public class MongoTemplateTest {

    @Autowired
   private MongoDbJpaService  mongoDbJpaService;
    /**
     * 测试mongo jpa 新增
     */
    @RequestMapping("/save")
    public ResponseData testSave(MongoJpaBookVo vo){
        mongoDbJpaService.save(vo);
        return ResponseData.success();
    }

}
