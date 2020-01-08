package com.dy.cool.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;

/**
 * Spring 线程池配置
 *
 * @author:ice
 * @Date: 2018/6/7 15:16
 **/
@Configuration
public class ThreadPoolConfiguration {

    private int MAX_LENGTH = 100000;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        this.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        this.threadPoolTaskExecutor.setMaxPoolSize(4);
        this.threadPoolTaskExecutor.setCorePoolSize(4);
        this.threadPoolTaskExecutor.setQueueCapacity(MAX_LENGTH);
        this.threadPoolTaskExecutor.setKeepAliveSeconds(0);
        return this.threadPoolTaskExecutor;
    }

    @PreDestroy
    public void destroy() {
        if (threadPoolTaskExecutor != null) {
            threadPoolTaskExecutor.shutdown();
        }
    }

}
