package com.example.demo.service;

import com.example.demo.domain.Cron;

import java.util.List;

public interface CronService {

    Cron findById(String id);

    void insert(Cron cron);

    void modify(Cron cron);

    void delete(String id);

    List<Cron> queryByStatus();

}
