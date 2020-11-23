package com.ylz.springboot.modules.common.service.impl;

import com.ylz.springboot.commons.exception.ParamException;
import com.ylz.springboot.modules.common.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author lhh
 * @Date 2019/11/24 21:48
 */
@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from.address}")
    private String from;


    /**
     * 发送纯文本邮件
     *
     * @param toAddress 目标邮箱（收件人）
     * @param cc        抄送人
     * @param subject   主题
     * @param content   内容
     */
    @Override
    public void sendTextMail(String toAddress, String cc, String subject, String content) {
        if (StringUtils.isBlank(toAddress)) {
            throw new ParamException("邮件接收方不能为空");
        }
        if (StringUtils.isBlank(subject)) {
            throw new ParamException("邮件主题不能为空");
        }
        if (StringUtils.isBlank(content)) {
            throw new ParamException("邮件内容不能为空");
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            //发送者
            message.setFrom(from);
            //接受者
            message.setTo(toAddress);
            message.setCc(cc);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new EmailException("【邮件发送异常】" + e.getMessage());
        }
    }

    /**
     * 发送包含HTML文本的邮件
     *
     * @param to      目标邮箱（收件人）
     * @param cc      抄送人
     * @param subject 主题
     * @param content 内容
     * @throws Exception
     */
    @Override
    public void sendHtmlMail(String to, String cc, String subject, String content) {

        if (StringUtils.isBlank(to)) {
            throw new ParamException("邮件接收方不能为空");
        }
        if (StringUtils.isBlank(subject)) {
            throw new ParamException("邮件主题不能为空");
        }
        if (StringUtils.isBlank(content)) {
            throw new ParamException("邮件内容不能为空");
        }
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(from);
            helper.setTo(to);
            if(StringUtils.isNotBlank(cc)){
                helper.setCc(cc);
            }
            helper.setSubject(subject);
            // 启用html
            helper.setText(content, true);
            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送带附件的邮件
     *
     * @param to      目标邮箱
     * @param subject 主题
     * @param content 内容
     * @param file    附件
     */
    @Override
    public void sendAttachFileMail(String to, String subject, String content, File file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // multipart模式
        try {

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(from);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(), file);
            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带图片资源的邮件
     *
     * @param to      目标邮箱
     * @param subject 主题
     * @param content 内容
     * @param file    附件
     */
    @Override
    public void sendMailWithImg(String to, String subject, String content, File file) {

    }


    /**
     * 发送包含内嵌图片的邮件
     *
     * @throws Exception
     */
    public void sendAttachedImageMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // multipart模式
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo("miles02@163.com");
        mimeMessageHelper.setFrom("miles02@163.com");
        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
        // cid为固定写法，imageId指定一个标识
        sb.append("<img src=\"cid:imageId\"/></body>");
        sb.append("</html>");

        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);

        // 设置imageId
        FileSystemResource img = new FileSystemResource(new File("E:/1.jpg"));
        mimeMessageHelper.addInline("imageId", img);

        // 发送邮件
        mailSender.send(mimeMessage);

        System.out.println("邮件已发送");
    }
}
