package com.example.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    
    @Scheduled(fixedRate = 15000) // 每5秒执行一次
    public void taskWithFixedRate() {
        System.out.println("定时任务执行 - fixedRate");
    }
    
    @Scheduled(cron = "0 0 12 * * ?") // 每天中午12点执行
    public void taskWithCronExpression() {
        System.out.println("定时任务执行 - cron");
    }
}