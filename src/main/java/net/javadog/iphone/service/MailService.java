package net.javadog.iphone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.iphone.entity.MailEntity;

/**
 * 邮件接口
 *
 * @author hdx
 */
public interface MailService extends IService<MailEntity> {

    /**
     * 测试邮箱连通性
     *
     * @param mailEntity 邮件信息
     */
    void testMail(MailEntity mailEntity);

}
