package net.javadog.iphone.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.iphone.common.response.ResponseData;
import net.javadog.iphone.entity.IphoneEntity;

/**
 * iphone接口
 *
 * @author hdx
 */
public interface IphoneService extends IService<IphoneEntity> {

    void topicNotify();
}
