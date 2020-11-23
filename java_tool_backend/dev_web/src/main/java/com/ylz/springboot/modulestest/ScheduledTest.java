package com.ylz.springboot.modulestest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述:spring　task 定时任务测试
 *
 * @author liuhuihu
 * @create 2020-06-09 0:19
 */
@Component
@Async
public class ScheduledTest {


    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        System.out.println("=====>>>>>使用cron  {}"+System.currentTimeMillis());
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        System.out.println("=====>>>>>使用fixedRate{}"+System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        System.out.println("=====>>>>>fixedDelay{}"+System.currentTimeMillis());
    }
}
