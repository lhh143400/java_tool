package com.ylz.springboot.modules.elasticsearch.service;

import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author lhh
 * @Date 2019/9/16 23:12
 */
public interface EsRepositoryService {


    /**
     * 批量新增
     * @param list 书籍对象
     */
    public void batchSave(List<ArticleBook> list);


    /**
     * 单条新增
     * @param obj 书籍对象
     */
    public void save(ArticleBook obj);

    /**
     * 单条删除
     *
     * @param id 指定id 删除
     */
    public void delId(String id);

    /**
     * 批量删除
     *
     */
    public void batchDelete();

    long count();


    void delete(ArticleBook vo);

    Iterable<ArticleBook> getAll();

    /**
     * 分词搜索 author
     */
    List<ArticleBook> getByBookName(String name);

    Page<ArticleBook> pageQuery(Integer pageNo, Integer pageSize, String kw);
}
