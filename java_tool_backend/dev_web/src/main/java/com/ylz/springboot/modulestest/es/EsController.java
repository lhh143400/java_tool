package com.ylz.springboot.modulestest.es;

import com.alibaba.fastjson.JSON;
import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.modules.elasticsearch.pojo.ArticleBook;
import com.ylz.springboot.modules.elasticsearch.service.EsRepositoryService;
import com.ylz.springboot.modules.elasticsearch.service.EsTemplateService;
import com.ylz.springboot.utils.KeyGenerateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lhh
 * @Date 2019/9/17 0:44
 */
@RestController
@RequestMapping("/es_repo_test")
public class EsController {
    @Autowired
    private EsRepositoryService esRepositoryService;
    @Autowired
    private EsTemplateService esTemplateService;

    @ApiOperation(value = "批量新增测试", notes = "批量新增测试")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public ResponseData batchSave() {
        esRepositoryService.batchSave(batchSaveTest());
       // ArticleBook book = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("人间失格").author("太宰治").bookReport("《人间失格》是一部极端现实的颓废式自传体小说。主角啊叶是一位有心理病与生活格格不入的极少数人。开头就是“我一直过着一种耻辱的生活”，笔调极度消沉。看过其他评论，不能领悟本书主角者很多，其实不难理解，毕竟现代娇生冠养和有钱人太多，很多苦是不为人知。其实我很同情阿叶，一直作为二流写手的我，他的那种生活大有多相似之处（但没有阿叶那么一度地自我毁灭与婚外烂情），一直陪伴阿叶的酒和女人，也是作者大想摆脱的。然而我们这些人呢？一种无止境被钱牵着走的人，其实也没有多大意义，我觉得。于是我写过 （1）少女 高傲伪装着的脂粉脸吸引了一张张欲望及丑陋的异性老脸，紧绷的短裤里，呆着一群狼牙咧齿在撕抓。在撕抓或高潮到来时的满嘴胡话，在探出头来时被少女拉上拉链那刻封死在里面。 在 清晨依然，少女还是挂起永不卸妆的脸，吸引着过往人群欲火燃烧的目光，然而，当晚上窗帘拉上那刻，她仅仅是个小尤物。 （2）货与 老脸 一张张丑陋的脸在争几件货的那刻，散发出死人般的腐臭。尘世间的浮躁开启几百盏嘎吱响的老风扇转起，身上散发出的夏热，笼罩着每位将窒息的异乡人。于旧楼车间中，四毛钱的单价把那位老车位扭曲了老脸，一件衣服上的你尔我乍或有意无心的暧昧，突然钉死在一张漂白的工资单里。 （3）在钱下面 坐落在疲惫的白炽灯下的疲倦孤影，双手把失望缝进冰冷的齿轮里。但，伤人的是，泪水沾湿在十件成品八件次品里。 在吃完几年不变的每天两餐包菜萝卜晚饭后，独自一人在白炽灯下煎熬着。泛白的灯光在一声：“物价上涨了！”中瘫痪在冰冷平车桌面上 冰冷的桌面散发出百转千回的“呼——呼——呼——”声，啊！我亲爱朋友们，这是多么嘶哑的声音啊！我们过着一种有声音的哑剧生活！奥，这又该是多么无奈的年代呦！一种无可选择的生活！ 整天浸泡在满楼灰尘的车间，各种 方言安慰不下一颗异乡溺小的心。窗外，厂门口停放着的纸壳轿车带不走异乡人一脸不满和哀鸿遍野，带不走，带不走。。。。。。。。。。。。。。” 哀愁").build();
       // esRepositoryService.save(book);
        return ResponseData.successMessage("成功");
    }


    /**
     * 模拟书籍的读后感数据----从当当网摘抄
     */
    public List<ArticleBook> batchSaveTest() {
        ArticleBook book = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("人间失格").author("太宰治").bookReport("《人间失格》是一部极端现实的颓废式自传体小说。主角啊叶是一位有心理病与生活格格不入的极少数人。开头就是“我一直过着一种耻辱的生活”，笔调极度消沉。看过其他评论，不能领悟本书主角者很多，其实不难理解，毕竟现代娇生冠养和有钱人太多，很多苦是不为人知。其实我很同情阿叶，一直作为二流写手的我，他的那种生活大有多相似之处（但没有阿叶那么一度地自我毁灭与婚外烂情），一直陪伴阿叶的酒和女人，也是作者大想摆脱的。然而我们这些人呢？一种无止境被钱牵着走的人，其实也没有多大意义，我觉得。于是我写过 （1）少女 高傲伪装着的脂粉脸吸引了一张张欲望及丑陋的异性老脸，紧绷的短裤里，呆着一群狼牙咧齿在撕抓。在撕抓或高潮到来时的满嘴胡话，在探出头来时被少女拉上拉链那刻封死在里面。 在 清晨依然，少女还是挂起永不卸妆的脸，吸引着过往人群欲火燃烧的目光，然而，当晚上窗帘拉上那刻，她仅仅是个小尤物。 （2）货与 老脸 一张张丑陋的脸在争几件货的那刻，散发出死人般的腐臭。尘世间的浮躁开启几百盏嘎吱响的老风扇转起，身上散发出的夏热，笼罩着每位将窒息的异乡人。于旧楼车间中，四毛钱的单价把那位老车位扭曲了老脸，一件衣服上的你尔我乍或有意无心的暧昧，突然钉死在一张漂白的工资单里。 （3）在钱下面 坐落在疲惫的白炽灯下的疲倦孤影，双手把失望缝进冰冷的齿轮里。但，伤人的是，泪水沾湿在十件成品八件次品里。 在吃完几年不变的每天两餐包菜萝卜晚饭后，独自一人在白炽灯下煎熬着。泛白的灯光在一声：“物价上涨了！”中瘫痪在冰冷平车桌面上 冰冷的桌面散发出百转千回的“呼——呼——呼——”声，啊！我亲爱朋友们，这是多么嘶哑的声音啊！我们过着一种有声音的哑剧生活！奥，这又该是多么无奈的年代呦！一种无可选择的生活！ 整天浸泡在满楼灰尘的车间，各种 方言安慰不下一颗异乡溺小的心。窗外，厂门口停放着的纸壳轿车带不走异乡人一脸不满和哀鸿遍野，带不走，带不走。。。。。。。。。。。。。。” 哀愁").build();
        ArticleBook book1 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("人间失格").author("太宰治").bookReport("　1、与其苟延残喘，不如从容自戕 \n" +
                "　　 \n" +
                "　　 妈妈，我生活在这个世界上，但感受不到任何快乐。我厌倦了，早就厌倦了，从我出生的那天开始，我厌倦了爱情、友情和所有一切感情，在我眼里，它们是那么的虚假矫饰，令我作呕；妈妈，我是个孤独的人，我不想说话，也不愿思考，我的灵魂早已不翼而飞，我活着如同一具行尸走肉；妈妈，在这一刻我想到了死，那是唯一能让我得到救赎的方式，我对这肮脏的人世间已经不存任何希望，堕落的脚步每时每刻不曾停顿。妈妈，请您宽恕我最后一次的任性，因为我已经活得筋疲力尽；妈妈，现在这个放任自流的孩子终于要坚强一次了，千万不要为我流下哪怕一滴眼泪，但请在我的墓上洒满洁白的花瓣。 \n" +
                "　　 \n" +
                "　　2、 在怒奔的火车头阴影下看岸边日落，山景黄昏孤影幻化 \n" +
                "　　 \n" +
                "　　 太宰治，日本无赖派大师，1909年出生于日本青森县北津轻郡贵族家庭，父亲是当地首要人物，母亲体弱多病，不能亲自抚养孩子，所以在十个孩子中排行第九的太宰治自小他由姑母及保母照顾，父亲的严厉与母亲的缺位让太宰治从小心思纤细而敏感。太宰治在初中时后开始创办同人刊物，从此决心以文学为业。大学时期太宰治积极参加左翼运动，同时开始过着放浪不羁的生活，曾与艺妓同居，毕业后走向消极，其间四次自杀未遂，三十九岁时与最后一位爱人相约投水自尽。以晚期的《斜阳》与《人间失格》为人称道，被誉为战后日本文学的金字塔作品。 \n" +
                "　　 \n" +
                "　　3、请问上天：不抵抗何罪之有？ \n" +
                "　　 \n" +
                "　　 这个城市永远是那么的潮湿，一年里有十个月在下雨，单薄的衣裳抵挡不住这刺骨的风。我拎着最后的半瓶酒跌跌撞撞，没走两步就要撕心裂肺地咳几下。猛然间我的双腿无力，跪倒在地，空气肮脏的灰尘进入我那被烟草熏黑的肺里面，顿时袭来一阵恶心，我想伴着我的呕吐物一起入眠。现在的我活在这个世界上，没有幸福，也没有任何不幸。我丧失了为人的资格。我已经完全被人世间除名了。我今年二十一岁，白发明显增多，在一般人看来，我已是四十岁以上的年龄了。 \n" +
                "　　 \n" +
                "　　4、我曾是梦想的殉道者，现在却成了自我的终结者 \n" +
                "　　 \n" +
                "　　 他们都说，我是像神一样纯正的孩子。 \n" +
                "　　 但我却祈求天使与我同床。我要尽我所能地作践这个世界，作践自己。 \n" +
                "　　 我善于揣度，能猜到每个人的内心。也正因如此，我只能像个小丑一样的苟活于人世。 \n" +
                "　　 我孤独敏感，任何蛛丝马迹都能左右我的悲喜。 \n" +
                "　　 我攥拳而笑，令人不寒而栗。 \n" +
                "　　 心底的魔，早已将我准确撕裂。 \n" +
                "　　 \n" +
                "　　5、地狱即景，怒放的恶之花 \n" +
                "　　 \n" +
                "　　 相信没有一个人在读过这本书之后会不为所动，因为那分明是一个滴血灵魂的自白，它融合了世间所有的消极、麻木以及一切丑恶。那是无论怎样的经历和自省都没法抹去的一道污迹，他坦诚赤裸地剖析自己的内心，让人不得喘息，没法回避。灵肉分离是人存于世唯一的方式，他显然知道这一点，所以义无反顾的数次摒弃自己的生命。于他而言，生命既是痛苦，是负罪，又何谈珍贵？ \n" +
                "　　他赤裸着跌入急湍漩涡之中，仿佛再一次进入母亲的腹中，如此温暖，安逸。甚至没跟这个混乱不堪的世界道一声晚安，甚至露出了此生唯一一次发自内心的微笑。 \n" +
                "　　 他，仍像神一样纯正。 ").build();
        ArticleBook book2 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("人间失格").author("太宰治").bookReport("无论身逢乱世还是太平年间，最大的兵荒马乱到底都是幻灭” \n" +
                "　　 人间从来就是失格，没有平衡过。人最大的悲哀就是从来认为这个世界不是你存在的世界，你也从来没有这个世界上任何的一个角落存在或者证明自己存在过，看过人间失格片子后再看书后，同样的问题又出来了，每个角落都有些边缘人，他们怎么存在，他们怎么生活或者生存，这个问题，这是个从来都不能解决的问题。 \n" +
                "　　刺青，打斗，自杀，抑郁从来没有拿到台面上来解决这个问题，青年是什么，年青又是什么，突然想到《三枪》里面有一句台词，“你就别糟蹋青春了！”因为它稍纵即逝，从来没有留下过任何的痕迹，当你意识到它就这么走远的时候，已经没有了。 \n" +
                "　　 对绝望感到绝望的人，我从没有勇气接触，因为他们的世界过于强大，也过于伟大，仿佛生命生来就是折磨，也是历练，“他除了成为文学家之外没有别的选择，他必须不依赖任何之物，不把任何之物作为自己的支撑，却以此把一切变成自己的组成部分。” \n" +
                "　　 作者偏激，所以只能选择死亡。是种解脱。").build();

        ArticleBook book3 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("红星照耀中国").author("埃德加·斯诺").bookReport("红军不怕远征难，万水千山只等闲。”此乃红军气概。恰如斯，他们才走完，六千英里遥遥长征路，虽坎坷崎岖，但他们终望见了“长城内外，惟余莽莽；大河上下，顿失滔滔。”那“正西风落下长安，飞鸣镐。”的战争年代，他们知道“多少事，从来急，天地转，光阴迫。”但是“一万年太久，只争朝夕”“要扫除一切害人虫，全无敌”这是毛主席的誓言，亦是红军战士的誓言。").build();
        ArticleBook book4 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("红星照耀中国").author("埃德加·斯诺").bookReport("《红星照耀中国》，又称《西行漫记》，作者埃德加·斯诺。主要讲述当时红色中国正在发生着的事情，作者采访了毛泽东等一系列红军的领导人物，记述了长征过程中的一些事情和革命根据地发展现状。令我印象最深的应该是那些“红小鬼”了。 他们非常的有志气，不允许别人叫他们“喂”或者“嗨”，他们只接受“同志”或“红小鬼”这些称呼。倘若有人不遵守，他们也会漠视那一个人，直到他改正错误。 他们年龄与我们相当，大都十五六岁，有的甚至十三岁时已经参加了红军。在西北苏区一共有少年先锋队员约4万名。他们在红军中担任不同的职位，在属于自己的职位上，他们认真负责。在他们中有的甚至都义无反顾的走上了战场，哪怕枪几乎与她的身子一般高。“在蒋介石的江西共匪感化院里，许多被俘的“红军”是十岁至十五岁的少年。”有的从战场上回来，伤势严重，头缠血绷带，有的甚至处于昏迷。但他们乐此不疲。 “有革命的地方就是好地方。我们吃什么，是在哪里，都不重要，重要的是革命”千篇一律的回答显示着“红小鬼”们对革命的信任，对它的支持。他们对红军对外的形象也非常的重视，不愿在外国人面前留下一个坏印象。 无法想象，十四五岁还很稚嫩的他们已经经验丰富，“但是他们的刚毅坚韧精神令人叹服，他们对红军的忠贞不贰，始终如一，只有很年轻的人才能做到”这一切源于他们刚毅坚韧的精神，我们要向他们学习，有像他们一样努力。").build();
        ArticleBook book5 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("红星照耀中国").author("埃德加·斯诺").bookReport("《红星照耀中国》是一部文笔优美的纪实性很强的报道性作品。作者记录了自1936年6月至10月在陕甘宁革命根据地进行实地采访的所见所闻。斯诺作为一个西方新闻记者，对中国共产党和中国革命进行了客观评价，向全世界报道了中国共产党和红军的真实情况，采访了众多中国共产党领袖和红军将领。 书中有一人物想必大家都知道。毛泽东，毛泽东是一个睿智而博学多才的知识分子。他熟读世界历史，熟知世界政治，是个认真研究哲学的人；他是天才的军事家和政治战略家。 其中《红小鬼》这一章中说的是有一些孩子才十岁就当上了红军，其后一生就走上了革命的道路，在每一个红军驻地，都会有一个少年先锋队的模范连，他们都是从全国各地来的十几岁的少年。 我们现在的年龄和他们相仿，但是，相比之下我们比他们幸福的太多了，就拿吃的来说吧！我们现在不论大人还是小孩吃不完的食物都直接倒掉，而他们那时想吃饱都难，经常靠树皮和腰带还有树根来充饥。 红军的精神，是一笔精神财富，激励着中国后来人。红军将士们为了崇高理想而百析不挠，奋勇拼搏的革命精神。从这里给我的启示是：我们不能辜负战士们对我们的期望，要勤奋学习，做一个祖国的栋梁，为祖国做更多的贡献。 这本书的作者埃德加·斯诺，他是一位 美国新闻工作者，来到动乱的中国在报社工作。埃德加·斯诺常年跟随红 军共同生活，并时常和中 共的领导面对面交流，他与中 共主 要领导结下深厚的友谊，依据切身体验而着此书，其真实的经历和感触是其写作的基石。").build();

        ArticleBook book6 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("古汉语常用字字典（第5版）").author("王力 岑麒祥 林焘").bookReport("儿子面临小升初，语文学习中的文言文阅读理解是其一大难点，用此书作为文言文阅读的工具书再好不过。本字典收古汉语常用字6400余个，简体字作头，标明繁体字，列有本义、引申义、假借义，并有例句，读来通俗易懂，简单明了，是学习文言文的好帮手。").build();
        ArticleBook book7 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("古汉语常用字字典（第5版）").author("王力 岑麒祥 林焘").bookReport("《古汉语常用字字典》是1979年商务印书馆出版的图书，作者是王力、岑麒祥、林焘、戴澧、唐作藩、蒋绍愚、张万起、徐敏霞。[1] 该书是新中国成立后第一部用现代语言学和辞书学观点、方法编写的古汉语权威字典。").build();
        ArticleBook book8 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("古汉语常用字字典（第5版").author("王力 岑麒祥 林焘").bookReport("听说这本字典是初中孩子的必备，赶上有活动果断拿下，现在小学高年级已经开始学一点小古文了，早晚都会用得上的。还有一本更厚的古代大字典，那个拿着还是不方便，这个可以书包里装着随时用。").build();

        ArticleBook book9 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName(" 活着（2017年新版）").author("余华").bookReport("因为远离那些动荡的年月，因为并未真正有过艰难和困顿，这个故事让年青的我们不禁有些战栗。薄薄的十二万字，笼罩着“欲哭无泪的压抑”。只是阖上书本之时，内心似乎多了一些超越世俗欲望和纷争的平静。现实生活的无情与残忍，远比我们想象的要宽广；而活着，纵使要担当诸多难以承纳的苦痛，但是依然要坚忍，顽强。这应当便是生命的力量罢。").build();
        ArticleBook book10 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("活着（2017年新版）").author("余华").bookReport("有人说，小说的故事情节显得过于戏剧化，其实人生如戏，没有什么不可能的。而小说本身就是“讲故事”的艺术，很多时候，我们是不能去分析的。福贵的一生，经历了年少阔绰、家道败落、战争的离乱、丧妻失子、以至于连亲人都不再有一个。这些苦难于一个人来说，的确显得有一点戏剧化，但是谁又能否认，一个人尽管不一定会经历这同样的苦难，但是苦难同样会以另一种方式向人袭来呢？").build();
        ArticleBook book11 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("活着（2017年新版）").author("余华").bookReport("我走在老街上的时候看到的是一张张岁月腐蚀的脸，深深的皱纹如同天堑般的沟渠，在曾经稚嫩的脸上无情地刻下属于时间这个恶魔的烙印，这才知道什么叫做老泪纵横；他们银白的头发记录的是过往的曾经，在巷子一边满满抽着香烟的老人，在烟雾缭绕中度过看似无事平静的一天。时而在回忆中失神，忘了烧到手边的光火").build();

        ArticleBook book12 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("三体").author("刘慈欣").bookReport("给时光以生命，而不是给生命以时光。").build();
        ArticleBook book13 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("三体").author("刘慈欣").bookReport("每个人的生命都会有结束的那一刻。一个人，或许长命百岁，或许英年早逝，总之长短不一。而生命的长短更不是我们主观上能够改变的。因为生活总是有太多的未知，没有人知道未来会发生什么，也没有人知道自己会遭遇什么").build();
        ArticleBook book14 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("三体").author("刘慈欣").bookReport("生命真正的悲哀不是终结，而是空洞。生命迟早会走到尽头，但倘若我们自己都不知道去珍惜那稍纵即逝的时光，而是虚度它，消磨它，那才是最大的遗憾").build();

        ArticleBook book15 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("小孩").author("大冰").bookReport("。喜欢的音乐在指间，美丽的风景在胸前，想去的远方在脚下，足矣。见过的路越多，越喜欢宅着。见过的人越多，越喜欢孩子").build();
        ArticleBook book16 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("小孩").author("大冰").bookReport("苦中作乐的小孩，知苦灭苦的小孩。\n" +
                "\n" +
                "赤子之心的小孩，自度度人的小孩。他只想讲故事，只会讲故事，只是讲故事").build();
        ArticleBook book17 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("小孩").author("大冰").bookReport("饮之甘之如饴，品之回味无穷。 读后悟出的三句箴言：努力突破自我，尝试多元人生，不要停止成长。书中有生死相依的壮族男女，有金不换的回头浪子，有破茧成蝶、华丽蜕变的成都姑娘，也有怀揣家国情怀的老兵。行大道于无形，言大爱于无声。其言其行乃为凡夫俗子望尘莫及，不能望其项背，其人其事不只感动你，更会震撼你！ 大冰的小屋，一个闪烁着希望之光的地方，一个助你圆梦的地方，一个创造奇迹的地方").build();

        ArticleBook book18 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName(" 我不敢说，我怕被骂").author("文/[荷兰]皮姆·范·赫斯特").bookReport("有时候，我们会听到孩子说，“对不起，爸爸妈妈，我做错事情了。”听到这样的话，我们的第一反应，会是因为这个错误而暴跳如雷，还是会首先安抚孩子害怕、恐惧的心情。不要小看这样一个小小的回应，因为，这会让孩子拥有完全不一样的成长经历。如果总是严厉批评孩子的错误，也许，有一天，比错误本身更严重的，是再也难以恢复的信任感，以及隐瞒带来的更严重的后果。").build();
        ArticleBook book19 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName(" 我不敢说，我怕被骂").author("文/[荷兰]皮姆·范·赫斯特").bookReport("书中主人翁莫伊拉有时笨手笨脚的，还有点儿淘气。她不小心把裤袜弄破了，却不敢告诉妈妈，她把秘密藏在了肚子里；因为不爱吃梨，她把梨扔进了*桶，又把这个秘密也藏进肚子里；下午放学后，灾难降临到妈妈的婚纱上，天哪，这事儿绝对不能让妈妈知道！晚饭时她什么也吃不下，因为肚子里塞满了秘密，实在没地儿啦。这是一个风趣幽默又感人至深的故事，莫伊拉最终发现：把肚子里的小秘密告诉爸爸妈妈，感觉会更好。         本书可以被归类为“富有教育意义”的绘本，而且它对父母的教育意义远多于对孩子。它让父母更了解孩子的内心，也给我们示范了面对这种问题时要如何加以应对。 “接纳”一词，听着容易，但其实我们经常会把“不接纳”误解成“教育”。原因在于，我们处在强势地位，会用我们的思维方式来理解孩子的一切，还把这个过程称为“教育”，称为“纠正”。这就是为什么这个故事有这样一个题目，它其实是很多孩子的心声：我不敢说，我怕被骂。         这是符合孩子心理的书，听标题就很有感触，有时候孩子不敢说是因为怕被骂，我们成人需要更尊重孩子的内心。感觉这本书就是为我量身定做的一样，因为我就是一个比较强势的妈妈，经常不注意言语而伤害到孩子的自信心，我平时在教育孩子时属于严厉型，有时候孩子做错事，我常常控制不住言辞犀利一针见血毫无情面可讲。这本书对我的教育意义很大，分享给大家，希望这本书可以让孩子愿意与你沟通，讲出他们的心事，帮助爸爸妈妈学习怎样给予孩子安全感，提高孩子自我接纳程度。").build();
        ArticleBook book20 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName(" 我不敢说，我怕被骂").author("文/[荷兰]皮姆·范·赫斯特").bookReport("“最糟糕的事情是，跟最亲密的人说谎，而他相信了！”       孩子成长的过程当中（2-4岁），总会有一些小秘密不想告诉粑粑麻麻，因为害怕被骂。日积月累，可能孩子就会习惯性的用其他的语言来掩饰这些小秘密，最后变成我们不愿意看到的结果——说谎。       那么，这时候就需要我们父母来告诉孩子：不要怕，不管发生了什么，我们都是最爱你的！       也许你会说，这一本绘本并不能改变什么。但是，没试过你怎么知道不行呢？").build();

        ArticleBook book21 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("我喜欢生命本来的样子").author("周国平").bookReport("生命要求每个人要学会孤独自省，要独自成长，要用力去爱，要去拥抱，要探索灵魂。在人世间的一切责任中，最根本的责任是对你自己的人生责任，真正成为你自己，活出你独特的个性和价值来。").build();
        ArticleBook book22 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("我喜欢生命本来的样子").author("周国平").bookReport("生命要求每个人学会孤独自省，要独自成长，要用力去爱，要去拥抱，要探索灵魂。成为你自己，第一有坚定的价值观，第二有清楚的自我认知，第三有强大的精神性自我").build();
        ArticleBook book23 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("我喜欢生命本来的样子").author("周国平").bookReport("需要独处，是为了进行内在整合。独处也是一种能力，处在喧嚣的闹市中，我们要学会去独处，减少不必要的社交成本。我天性不宜社交，在多数场合，我不是觉得对方乏味，就是害怕对方觉得我乏味。可是我既不能忍受对方的乏味，也不愿费劲使自己显得有趣，那都太累了。我独处时最轻松，以为我不觉得自己乏味，即时乏味，也不累及他人，无需感到不安。”可能这个状态也适合我吧，读来深有体会。").build();

        ArticleBook book24 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("非暴力沟通").author("（美）马歇尔?卢森堡著").bookReport("我充分地感受到了“怎么说”比“说什么”更重要。非暴力沟通不仅能促进自我理解和内心的和谐，还能痊愈我们内心和人际关系的创伤，成为弥补裂痕和仇隙的黏合剂").build();
        ArticleBook book25 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("非暴力沟通").author("（美）马歇尔?卢森堡著").bookReport("如果一页中有非常重要的内容，就用简明的文字列在该页的天头，打开这一页，一目了然，便于读者快速把握内容重点。这一独出机杼的设计非常实用， 读者完全可以只读天头的文字，就能提纲挈领地复习回顾和掌握全书内容").build();
        ArticleBook book26 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("非暴力沟通").author("（美）马歇尔?卢森堡著").bookReport("《非暴力沟通》提供的模式就是抓住观察、感受、需要、请求四个要素，沟通时按照我看到了什么，我的感受如何，哪些需要（或价值、愿望等）导致那样的感受，为了改善生活我的请求是什么，顺序展开这个过程就可以了").build();


        ArticleBook book27 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("皮囊-文学").author("蔡崇达").bookReport("皮囊啊，温暖的、逸乐的、疼痛的、脆弱的、可耻的皮囊，这些都是我们的皮囊。在书中我读到生活中每时每刻都在发生的事，当这些事跃然纸上时，却又不是那么回事了，或许这便是这本书的魅力吧！刻在骨头里的故事，我们始终要回答的问题。").build();
        ArticleBook book28 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("皮囊-文学").author("蔡崇达").bookReport("这是一本真正让自己开启阅读生涯的书，因为看的第一本电子书就是《皮囊》，而后便开始了阅读之路。里面的文字很有感染力，是一本可以让自己安静的书，同时他里面记录不仅有现实意义的故事，还有感人的亲情。每个人物的性格，都在我们身边存在").build();
        ArticleBook book29 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("皮囊-文学").author("蔡崇达").bookReport("读这本书的过程中一直有种流泪的冲动，我以为是因为书写者能写出好的故事。直到看到最后马赛尔·普鲁斯特的一句话“每个读者只能读到已然存在于他内心的东西。书籍只不过是一种光学仪器，帮助读者发现自己的内心。”想流泪不过是迷惘太久的自己急需一个出口。").build();

        ArticleBook book30 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("丰子恺万般滋味，都是生活--文学").author("丰子恺").bookReport("所谓单纯，不是幼稚的天真，而是一种超越了世故，知道人世间种种矛盾、种种问题，种种细微的心理计较。\n" +
                "\n" +
                "但超过这一层，一种超乎其上的平视，达观看待这个世界，然后游戏其间。这是非常豁达的心胸，一种超越的态度和智慧。").build();


        ArticleBook book31 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("浮生六记--文学").author("沈复").bookReport("读沈复的书每使我感到这安乐的奥妙，远超乎尘俗之压迫与人身之痛苦。我们要学会用美的眼光，去发现周遭的一切。人生已经如此的艰难，有些事情就不要去拆穿。愿你的生活如果贺卡上的祝词欢脱；愿你能因为某个人的出现而让世界丰盈；愿这悠长岁月温柔安好，有回忆煮酒，有情调配茶；愿你没有软肋，也不需要铠甲；愿我们和爱的人浪费余生，热泪盈眶，长生不老；").build();


        ArticleBook book32 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("人间词话--文学").author("王国维").bookReport("《人间词话》中的三重境界，第一个境界是，要像思念一个爱人一样，对待自己的事业，你一定要树立一个人生的目标，因为没有目标，人生就像是一艘没有航向的船，随风飘荡而已，也许永远在原地打转，而无法前进到自己的彼岸。人生是需要彼岸精神的。第二个境界是，你要像爱一个人一样，付出自己的努力。就像是谈恋爱，你有心爱的人，你不去努力追求他，爱人很可能就跟别人跑了。同样，对待自己的事业，你不去努力求索，哪里会有人生的成功？成功需要爱情一样的求索。第三个境界，有点像道家的境界。你有了目标，有了努力，但是能不能成功，还要看你的时机和命运，有的时候，你不能太急，人生就像是橡皮筋，纵然可以拉伸的很长，但是你拉的太长了，很容易绷断，我们只需要在艰辛的努力之后，放松心态，也许成功，就在你不经意的不远处！").build();

        ArticleBook book33 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("文化苦旅--文学").author("余秋雨 ").bookReport("融入了对历史的深刻感悟：“每到一个地方，总有一种沉重的历史气压罩住我的全身，使我无端地感动，无端地喟叹⋯⋯我站在古人一定站过的那些方位上，用先辈差不多的黑眼珠打量着很少会有变化的自然景观，静听着与千百年前没有丝毫差异的风声鸟声，心想⋯⋯而中国文化的真实步履却落在这山重水复、莽莽苍苍的大地上。余秋雨的文化散文几乎是篇篇浸透了国文化的凄风苦雨和中国文人的集体痛苦感，再以个人生命的真体验和真性情浇铸成文字，他给自己散文确定了如下任务：“还历史以真实，还生命以过程。").build();

        ArticleBook book34 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("沉默的大多数--杂文随笔").author("王小波").bookReport("。《沉默的大多数》就是这套全集的第一卷杂文集中的第一篇文章。这套书即便不能算装帧精美，至少也是清爽大方，颇有特色了。用现在的话说既有了有趣的灵魂，也有了好看的皮囊。").build();

        ArticleBook book35 = ArticleBook.builder().id(KeyGenerateUtil.generateOracleId()).bookName("笑场--中国现当代随笔").author("李诞").bookReport("未曾开言我先笑场，笑场完了听我诉一诉衷肠，生活就是一摊狗屎，不扔何必捧着？生活真的欺骗了你，干啥要忍着，花钱能摆平的事，花呗，什么，你没钱，没钱挣去呀，赚了钞票，回来继续怼。我说的这些，和这本书有关系吗？有没有关系，你看不出来吗？看不出来，你千万要说，把你憋坏了我也不会改。").build();


        List<ArticleBook> list = new ArrayList<>();
        list.add(book);
        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);
        list.add(book6);
        list.add(book7);
        list.add(book8);
        list.add(book9);
        list.add(book10);
        list.add(book11);
        list.add(book12);
        list.add(book13);
        list.add(book14);
        list.add(book15);
        list.add(book16);
        list.add(book17);
        list.add(book18);
        list.add(book19);
        list.add(book20);
        list.add(book21);
        list.add(book22);
        list.add(book23);
        list.add(book24);
        list.add(book25);
        list.add(book26);
        list.add(book27);
        list.add(book28);
        list.add(book29);
        list.add(book30);
        list.add(book31);
        list.add(book32);
        list.add(book33);
        list.add(book34);
        list.add(book35);
        String json = JSON.toJSONString(list);
        System.out.println(json);
        return list;
    }
}
