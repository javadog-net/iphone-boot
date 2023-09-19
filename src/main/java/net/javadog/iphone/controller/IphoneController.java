package net.javadog.iphone.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.javadog.iphone.common.response.ResponseData;
import net.javadog.iphone.entity.IphoneEntity;
import net.javadog.iphone.service.IphoneService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * iphone控制层
 *
 * @author hdx
 */
@RestController
@RequestMapping("/iphone")
public class IphoneController {

    @Resource
    private IphoneService iphoneService;

    @PostMapping("/topic")
    public ResponseData topic(@RequestBody IphoneEntity iphoneEntity){

        LambdaQueryWrapper<IphoneEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(IphoneEntity::getModel,iphoneEntity.getModel())
                .eq(IphoneEntity::getLocation,iphoneEntity.getLocation())
                .eq(IphoneEntity::getMail,iphoneEntity.getMail());
        IphoneEntity dbIphoneEntity = iphoneService.getOne(queryWrapper);
        if(ObjectUtil.isNotNull(dbIphoneEntity)){
            return ResponseData.error("已订阅此型号，请勿重新订阅");
        }
        iphoneEntity.setCreateTime(new Date());
        iphoneService.saveOrUpdate(iphoneEntity);
        return ResponseData.success();
    }
}
