package com.ylz.springboot.modules.elasticsearch.pojo;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 书籍 好的摘要文章
 *
 * @author lhh
 * @Date 2019/9/16 23:05
 */
@Document(indexName = "article_book" ,type = "book")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBook {

    private String id;
    /**
     * 书本名字
     */
    private String bookName;
    /**
     * 作者
     */
    private String author;
    /**
     * 读后感，文章摘要
     */
    private String bookReport;
}
