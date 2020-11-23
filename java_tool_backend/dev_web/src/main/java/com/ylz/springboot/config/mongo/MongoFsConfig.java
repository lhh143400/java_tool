package com.ylz.springboot.config.mongo;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * @author lhh
 * @Date 2019/9/13 20:16
 */
@Configuration
public class MongoFsConfig {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Bean
    public GridFSBucket gridFSBucket(){
        return GridFSBuckets.create(mongoDbFactory.getDb());
    }
}
