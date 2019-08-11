package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;


@Configuration
public class ScheduleConfig {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        //todo:注入调度线程池到容器中
        return new ThreadPoolTaskScheduler();
    }

    //todo:将动态的定时任务id保存取来，方便后面取消
    private Map<String, ScheduledFuture<?>> futureMap = new HashMap<>();

    //todo:s定时任务表达式
    private String cron = "";

    //todo:设置任务表达式
    public void setCron(String cron){
        this.cron = cron;
    }

    //todo:取消定时任务
    public void stopTask(String id) {
        if (futureMap.get(id) != null) {
            futureMap.get(id).cancel(true);
            futureMap.remove(id);
        }
    }

    //todo:新建并开启定时任务
    public void startTask(String id, String cron, Runnable task){
        this.cron = cron;
        ScheduledFuture<?> future = taskScheduler.schedule(task, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if (StringUtils.isEmpty(cron)) {
                    return null;
                }
                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
        futureMap.put(id, future);
    }


}
