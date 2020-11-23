package com.ylz.springboot.modules.mongodb.vo;

import lombok.*;

/**
 * 书本--视图层
 *
 * @author lhh
 * @Date 2019/9/13 17:27
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MongoJpaBookVo {
    private String id;
    private String bookName;
    private String author;
}
