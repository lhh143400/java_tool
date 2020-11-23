package com.ylz.springboot.modules.mongodb.vo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 书本
 *
 * @author lhh
 * @Date 2019/9/13 17:27
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("jpa_book")
public class MongoTemplateBookVo {
    private String id;
    private String bookName;
    private String author;
}
