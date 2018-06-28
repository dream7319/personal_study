package com.springboot.mybatis.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * custom.field 为true则启用定时任务, false则禁用定时任务
 * @author lierlei@xingyoucai.com
 * @create 2018-06-27 17:44
 **/
@Component
@ConditionalOnProperty(value = {"custom.field"},
		matchIfMissing = false)
@EnableScheduling
@Slf4j
public class SchedulePrint {
	@Scheduled(cron = "* * * * * ?")
	public void sendSms(){
		log.info("------");
	}
}
