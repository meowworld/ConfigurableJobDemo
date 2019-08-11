package com.example.demo.web;

import com.example.demo.domain.Cron;
import com.example.demo.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @see : demoUrl:https://www.jianshu.com/p/77e07ee42f6e
 */

@RestController
@RequestMapping("/cron")
public class CronController {

    @Autowired
    private CronService cronService;

    @PostMapping("/insert")
    public Map insert(Cron cron){
        Map<String,Object> map = new HashMap<>();
        cronService.insert(cron);

        map.put("code", "0");
        map.put("msg", "定时任务新增成功");
        return map;
    }

    @PostMapping("/modify")
    public Map modify(Cron cron){
        Map<String,Object> map = new HashMap<>();
        cronService.modify(cron);

        map.put("code", "0");
        map.put("msg", "定时任务修改成功");
        return map;
    }

    @DeleteMapping("/{id}")
    public Map delete(@PathVariable String id){
        Map<String,Object> map = new HashMap<>();
        cronService.delete(id);

        map.put("code", "0");
        map.put("msg", "定时任务删除成功");
        return map;
    }

    @GetMapping("/{id}")
    public Map queryById(@PathVariable(value = "id") String id){
        Map<String,Object> map = new HashMap<>();
        Cron cron = cronService.findById(id);

        map.put("code", "0");
        map.put("data",cron);
        map.put("msg", "定时任务查询成功");
        return map;
    }
}
