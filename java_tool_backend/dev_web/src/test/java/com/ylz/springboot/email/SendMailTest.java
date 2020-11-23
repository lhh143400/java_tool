package com.ylz.springboot.email;

import com.ylz.springboot.modules.common.service.EmailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * 发送邮件测试
 *
 * @author lhh
 * @Date 2019/11/24 22:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailTest {
    private static final Logger logger= LoggerFactory.getLogger(SendMailTest.class);
    @Autowired
    private EmailService emailService;

    @Before
    public void before() {
        System.out.println("start=============");
    }
    @After
    public void after(){
        logger.info("邮件发送成功end====================");
    }
    /**
     * 测试发送简单邮件
     */
    @Test
    public void sendSimpleEmail(){
        //emailService.test();
        emailService.sendTextMail("1434004617@qq.com","987134295@qq.com","测试springboot Email 邮件主题1","测试邮件内容1");
    }

    /**
     * 发送包含HTML文本的邮件
     */
    @Test
    public void sendHtmlMail(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
        sb.append("</html>");
        emailService.sendHtmlMail("1434004617@qq.com","","Spring Boot Mail 邮件测试【HTML】",sb.toString());
    }
    /**
     * 测试发送带有附件的邮件
     */
    @Test
    public void sendAttachEmail(){
        // 设置附件
        FileSystemResource file = new FileSystemResource(new File("D:/tmp/5726.jpg"));
        emailService.sendAttachFileMail("1434004617@qq.com","Spring Boot Mail 邮件测试【附件】","邮件测试【附件】",file.getFile());
    }


}
