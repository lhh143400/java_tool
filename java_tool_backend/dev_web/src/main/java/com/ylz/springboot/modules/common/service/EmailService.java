package com.ylz.springboot.modules.common.service;

import java.io.File;

/**
 * 邮件逻辑层
 *
 * @author lhh
 * @Date 2019/11/24 21:48
 */
public interface EmailService {

    /**
     * 发送纯文本邮件
     *
     * @param toAddress  目标邮箱
     * @param cc 抄送人
     * @param subject 主题
     * @param content 内容
     */
    void sendTextMail(String toAddress, String cc,String subject, String content);

    /**
     * 发送包含HTML文本的邮件
     *
     * @param to 目标邮箱（收件人）
     * @param cc        抄送人
     * @param subject   主题
     * @param content   内容
     * @throws Exception
     */
     void sendHtmlMail(String to, String cc, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to  目标邮箱
     * @param subject 主题
     * @param content 内容
     * @param file  附件
     */
    void sendAttachFileMail(String to,String subject,String content,File file);


    /**
     * 发送带图片资源的邮件
     *
     * @param to  目标邮箱
     * @param subject 主题
     * @param content 内容
     * @param file  附件
     */
    void sendMailWithImg(String to,String subject,String content,File file);
}
