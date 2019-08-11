package com.example.demo.web;

import com.example.demo.config.ScheduleConfig;
import com.example.demo.domain.Cron;
import com.example.demo.service.CronService;
import com.example.demo.task.TaskInfo;
import com.example.demo.task.TaskTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ScheduleConfig scheduleConfig;
    @Autowired
    private CronService cronService;


    @PostMapping("/startTask")
    public String startTask(String id) {
        //从数据库查询定义的定时任务
        Cron cronData = cronService.findById(id);

        if(!StringUtils.isEmpty(cronData.getExpress())) {
            //创建线程
            TaskTemplate taskTemplate = new TaskTemplate();
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setName("meow");
            taskInfo.setData("哈哈哈哈");
            taskTemplate.setTaskInfo(taskInfo);
            scheduleConfig.startTask(cronData.getId(), cronData.getExpress(), taskTemplate);
            return "开启定时任务成功";
        } else {
            return "开启定时任务失败";
        }
    }

    @PostMapping("/stopTask")
    public String stopTask(String id) {
        scheduleConfig.stopTask(id);
        return "关闭定时任务成功";
    }

}
