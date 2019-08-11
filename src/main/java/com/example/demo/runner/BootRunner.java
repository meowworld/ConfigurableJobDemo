package com.example.demo.runner;

import com.example.demo.config.ScheduleConfig;
import com.example.demo.domain.Cron;
import com.example.demo.service.CronService;
import com.example.demo.task.TaskTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目启动的时候查找并执行所有符合条件的定时任务
 * @see {@link CommandLineRunner} 项目启动的最后回调执行
 */

@Component
public class BootRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootRunner.class);

    @Autowired
    private CronService cronService;

    @Autowired
    private ScheduleConfig scheduleConfig;

    @Override
    public void run(String... args) throws Exception {

        List<Cron> cronList = cronService.queryByStatus();

        cronList.forEach(cron->{
            scheduleConfig.startTask(cron.getId(), cron.getExpress(), new TaskTemplate());

            logger.info("-------- 定时任务设置完成 ---------");
            logger.info("taskId-->" + cron.getId());
        });

    }
}
