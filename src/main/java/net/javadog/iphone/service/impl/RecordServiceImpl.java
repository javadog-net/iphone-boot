package net.javadog.iphone.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.javadog.iphone.entity.RecordEntity;
import net.javadog.iphone.mapper.RecordMapper;
import net.javadog.iphone.service.RecordService;
import org.springframework.stereotype.Service;


/**
 * 记录接口实现类
 *
 * @author hdx
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordEntity> implements RecordService {

}
