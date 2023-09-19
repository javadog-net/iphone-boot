package net.javadog.iphone.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.javadog.iphone.common.response.ResponseData;
import net.javadog.iphone.entity.RecordEntity;
import net.javadog.iphone.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 记录控制层
 *
 * @author hdx
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/list")
    public ResponseData list(){
        LambdaQueryWrapper<RecordEntity> query = new LambdaQueryWrapper<>();
        query.orderByDesc(RecordEntity::getCreateTime).last("limit 0,10");
        return ResponseData.success(recordService.list(query));
    }
}
