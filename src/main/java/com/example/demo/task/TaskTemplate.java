package com.example.demo.task;

import com.example.demo.dao.CronDao;
import com.example.demo.service.CronService;
import com.example.demo.utils.ApplicationContextProvider;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新建一个定时任务线程 和 业务数据
 */

public class TaskTemplate implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TaskTemplate.class);

    //业务数据
    @Setter
    private TaskInfo taskInfo;
    
     @Autowired
    private ApplicationContextProvider contextProvider;

    //todo：线程开始执行

    @Override
    public void run() {
//        String name = this.taskInfo.getName();
//        String data = this.taskInfo.getData();

        logger.error(" ----- 定时任务开始执行 -----");

        CronService cronService = contextProvider.applicationContext.getBean(CronService.class);
        CronDao cronDao = contextProvider.applicationContext.getBean(CronDao.class);

//        logger.info("业务参数 name"+name);
//        logger.info("业务参数 data"+data);
    }

}
