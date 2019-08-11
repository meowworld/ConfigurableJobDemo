package com.example.demo.service;

import com.example.demo.dao.CronDao;
import com.example.demo.domain.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronServiceImpl implements CronService {
    @Autowired
    private CronDao cronDao;

    @Override
    public Cron findById(String id) {
        return cronDao.findById(id);
    }

    @Override
    public void insert(Cron cron) {
        cronDao.insertCron(cron);
    }

    @Override
    public void modify(Cron cron) {
        cronDao.modifyCron(cron);
    }

    @Override
    public void delete(String id) {
        cronDao.deleteCron(id);
    }

    @Override
    public List<Cron> queryByStatus() {
        return cronDao.queryByStatus();
    }


}