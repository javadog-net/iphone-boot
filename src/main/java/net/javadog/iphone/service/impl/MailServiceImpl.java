package net.javadog.iphone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.iphone.entity.MailEntity;
import net.javadog.iphone.mapper.MailMapper;
import net.javadog.iphone.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 邮件接口实现类
 *
 * @author hdx
 */
@Service
public class MailServiceImpl extends ServiceImpl<MailMapper, MailEntity> implements MailService {

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private MailService mailService;

    @Override
    public void testMail(MailEntity mailEntity) {
        String text = "测试邮箱是否可接收,欢迎访问https://www.javadog.net;" +
                "或关注微信公众：JavaDog程序狗。更多技术文章都在里面，欢迎访问";
        String subject = "JavaDog邮箱测试";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //主题
        mailMessage.setSubject(subject);
        //内容
        mailMessage.setText(text);
        // 接收邮箱
        mailMessage.setTo(mailEntity.getToMail());
        // 发送邮箱
        mailMessage.setFrom("postmaster@javadog.net");
        // 发送邮件
        javaMailSender.send(mailMessage);
        // 整合记录入库
        mailEntity.setCreateTime(new Date());
        mailEntity.setText(text);
        mailEntity.setFromMail("postmaster@javadog.net");
        mailEntity.setSubject(subject);
        mailService.saveOrUpdate(mailEntity);
    }
}
