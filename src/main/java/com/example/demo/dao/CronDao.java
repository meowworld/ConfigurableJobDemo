package com.example.demo.dao;


import com.example.demo.domain.Cron;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CronDao {

    Cron findById(@Param("id") String id);

    void insertCron(Cron cron);

    void modifyCron(Cron cron);

    void deleteCron(@Param("id") String id);

    List<Cron> queryByStatus();

}
