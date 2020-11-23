package com.ylz.springboot.modules.elasticsearch.pojo;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.List;

/**
 * 综合语料
 *
 * @author lhh
 * @Date 2019/9/24 19:38
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "corpus")
public class SynthesizeCorpus {
    /**
     * 主键
     */
    private String id;
    /**
     * 相似问题及标准问题
     */
    private List<String> question;
    /**
     * 语料答案
     */
    private String answer;
    /**
     * 语料类型
     */
    private String corpusType;
    /**
     * 创建时间
     */
    private Date createTime;
}
