package net.javadog.iphone.task;

import net.javadog.iphone.service.IphoneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 *
 * @author hdx
 */
@Component
public class ScheduledTask {

    @Resource
    private IphoneService iphoneService;

    @Scheduled(cron="${iphone.listen.corn}")
    public void reportCurrentTime() {
        iphoneService.topicNotify();
    }
}
