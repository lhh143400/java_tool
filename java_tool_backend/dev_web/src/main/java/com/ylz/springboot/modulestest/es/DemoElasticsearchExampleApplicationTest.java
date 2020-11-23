package com.ylz.springboot.modulestest.es;


        import com.alibaba.fastjson.JSON;
        import com.ylz.springboot.commons.consts.CorpusConst;
        import com.ylz.springboot.modules.elasticsearch.dao.EsRepository;
        import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
        import com.ylz.springboot.modules.elasticsearch.pojo.SynthesizeCorpus;
        import com.ylz.springboot.modules.elasticsearch.service.CorpusEsRepositoryService;
        import com.ylz.springboot.modules.elasticsearch.service.EsRepositoryService;
        import com.ylz.springboot.modules.elasticsearch.util.AssemblyCorpus;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.data.domain.Page;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.util.Arrays;
        import java.util.Date;
        import java.util.List;

/**
 * @author lhh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoElasticsearchExampleApplicationTest {

    @Autowired
    private EsRepositoryService commodityService;
    @Autowired
    private CorpusEsRepositoryService corpusEsRepositoryService;

    @Test
    public void contextLoads() {
        System.out.println(commodityService.count());
    }

    @Test
    public void testInsert() {
        ArticleBook commodity = new ArticleBook();
        commodity.setId("1501009001");
        commodity.setBookReport("原味切片面包（10片装）");
        commodity.setAuthor("101");
        commodity.setBookName("良品铺子");
        commodityService.save(commodity);

        commodity = new ArticleBook();
        commodity.setId("1501009002");
        commodity.setBookReport("原味切片面包（6片装）");
        commodity.setAuthor("101");
        commodity.setBookName("良品铺子");
        commodityService.save(commodity);

        commodity = new ArticleBook();
        commodity.setId("1501009004");
        commodity.setBookReport("元气吐司850g");
        commodity.setAuthor("101");
        commodity.setBookName("百草味");
        commodityService.save(commodity);

    }



    @Test
    public void testGetAll() {
        Iterable<ArticleBook> iterable = commodityService.getAll();
        iterable.forEach(e->System.out.println(e.toString()));
    }

    @Test
    public void testGetByName() {
        List<ArticleBook> list = commodityService.getByBookName("周平");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testPage() {
        Page<ArticleBook> page = commodityService.pageQuery(0, 10, "周国");
        System.out.println(JSON.toJSONString(page));
    }


    @Test
    public void testCorpus(){
            SynthesizeCorpus one=SynthesizeCorpus.builder().id("1").question(AssemblyCorpus.LIST_QUESTION_ONE)
                    .answer(AssemblyCorpus.LIST_ANSWER_ONE).corpusType(CorpusConst.PUA).createTime(new Date()).build();

        corpusEsRepositoryService.save(one);
    }

    @Test
    public void testParticipleSearch(){
        List<SynthesizeCorpus> list = corpusEsRepositoryService.getByQuestion("红墙");
        System.out.println(JSON.toJSONString(list));
    }
}