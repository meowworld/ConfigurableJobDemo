说明：

  本demo是基于SpringBoot 的 schedule 定时任务做的，Springboot 的 @Schedule 注解开启的定时任务
  能够动态的停止和启动，并修改执行cron表达式的参数，所以做了这个可基于页面配置的定时任务(页面还没有，只写了接口)
  
  数据库初始化脚本：resource/db/cron.sql
  
  1：BootRunner 是为了在项目启动的时候执行一些已经定义好的定时任务。
  
  2：TaskController 中有
    1):定时任务启动：根据接口参数id查找并启动
    2):定时任务停止：根据接口参数id查找并停止
    
  3：CronController 中是一套cron 定时任务定义的CRUD接口
  
  4：ApplicationContextProvider 是一个提供ApplicationContext的工具类，可以获取ApplicationContext，
     在ApplicationContext中可以获取到我们想要注入的业务bean
     
  5：ScheduleConfig 是任务启动和停止的类

//todo:
  这个demo 的定时任务，只能在本微服务中使用，不能实现A 微服务有一个定时任务，通过本demo调用执行。
  
  推介：xxlJob 是一个分布式的、可配置的定时任务。这个好像是当当网写的一个很好的轮子
  
  本demo只是个人为了学习而创建的
