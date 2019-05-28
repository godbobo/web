package com.aqzscn.www.global.component;

import com.aqzscn.www.global.domain.co.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件服务
 *
 * @author Godbobo
 * @date 2019/5/26
 */
@Component
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送简单邮件
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        // mailMessage.setCc(from);
        javaMailSender.send(mailMessage);
    }

    // 发送带附件的邮件
    public void sendMailWithFile(String to, String subject, String content, File file) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(), file);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw AppException.of("邮件发送失败，目标邮箱：" + to);
        }
    }

    // 发送模板邮件
    public void sendTemplateMail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw AppException.of("邮件发送失败，目标邮箱：" + to);
        }
    }

}