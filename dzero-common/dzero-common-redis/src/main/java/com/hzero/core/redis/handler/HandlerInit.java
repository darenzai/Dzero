package com.hzero.core.redis.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.darenzai.dzero.common.core.convertor.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import com.hzero.core.redis.DZeroRedisProperties;
import com.hzero.core.redis.RedisQueueHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


/**
 * 初始化
 *这个类的作用是在 Spring Boot 应用启动后，初始化 Redis 队列消费者监听器，并扫描所有带有 `@QueueHandler` 注解的 Bean。通过调用 `scanQueueHandler()` 方法，将扫描到的队列处理器注册到全局的队列处理器注册表中。然后，在判断是否扫描到了队列消费类之后，启动一个定时调度线程池，不断从 Redis 队列中读取消息并按照对应的队列进行处理。如果扫描到了队列消费类，则会开启消费线程，通过线程池调度执行将队列中的消息取出并调用对应的方法进行处理。
 *
 * 该类中的 `Consumer` 类定义了具体的消费线程类，它会不断地从 Redis 队列中取出消息并进行处理。通过该类，可以实现异步处理 Redis 消息队列，提高系统的并发能力。同时，通过该类，我们可以支持两种不同的队列处理方式：单个消息处理和批量消息处理。
 * @author shuangfei.zhu@hand-china.com 2019/10/23 16:15
 */
public class HandlerInit implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerInit.class);

    private final DZeroRedisProperties redisProperties;
    private final RedisQueueHelper redisQueueHelper;

    public HandlerInit(RedisQueueHelper redisQueueHelper, DZeroRedisProperties redisProperties) {
        this.redisQueueHelper = redisQueueHelper;
        this.redisProperties = redisProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        if (redisProperties != null && !redisProperties.isRedisQueue()) {
            // 若关闭redis队列则不扫描
            return;
        }
        boolean flag = scanQueueHandler();
        // 若扫描到队列消费类，则开启消费线程
        if (flag) {
            // 启动线程执行消费
            ScheduledExecutorService register =
                    new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder()
                            .namingPattern("redis-queue-consumer")
                            .daemon(true)
                            .build());
            register.scheduleAtFixedRate(new Listener(redisQueueHelper), 0, redisProperties == null ? 5 : redisProperties.getIntervals(), TimeUnit.SECONDS);
        }
    }

    /**
     * 启动后扫描QueueHandler注解
     */
    private boolean scanQueueHandler() {
        boolean flag = false;
        Map<String, Object> map = ApplicationContextHelper.getContext().getBeansWithAnnotation(com.hzero.core.redis.handler.QueueHandler.class);
        for (Object service : map.values()) {
            if (service instanceof com.hzero.core.redis.handler.IQueueHandler || service instanceof com.hzero.core.redis.handler.IBatchQueueHandler) {
                com.hzero.core.redis.handler.QueueHandler queueHandler = ProxyUtils.getUserClass(service).getAnnotation(com.hzero.core.redis.handler.QueueHandler.class);
                if (ObjectUtils.isEmpty(queueHandler)) {
                    LOGGER.debug("could not get target bean , queueHandler : {}", service);
                } else {
                    com.hzero.core.redis.handler.HandlerRegistry.addHandler(queueHandler.value(), service);
                    LOGGER.info("Start listening to the redis queue : {}", queueHandler.value());
                    flag = true;
                }
            }
        }
        return flag;
    }

    static class Listener implements Runnable {

        private final RedisQueueHelper redisQueueHelper;

        Listener(RedisQueueHelper redisQueueHelper) {
            this.redisQueueHelper = redisQueueHelper;
        }

        @Override
        public void run() {
            Set<String> keys = com.hzero.core.redis.handler.HandlerRegistry.getKeySet();
            if (CollectionUtils.isEmpty(keys)) {
                return;
            }
            keys.forEach(key -> com.hzero.core.redis.handler.HandlerRegistry.getThreadPool(key).execute(new Consumer(key, redisQueueHelper)));
        }
    }

    /**
     * 消费线程
     */
    static class Consumer implements Runnable {

        private final String key;
        private final RedisQueueHelper redisQueueHelper;

        public Consumer(String key, RedisQueueHelper redisQueueHelper) {
            this.key = key;
            this.redisQueueHelper = redisQueueHelper;
        }

        @Override
        public void run() {
            Object handler = com.hzero.core.redis.handler.HandlerRegistry.getHandler(key);
            if (handler == null) {
                return;
            }
            if (handler instanceof com.hzero.core.redis.handler.IQueueHandler) {
                while (true) {
                    String message = redisQueueHelper.pull(key);
                    if (StringUtils.isBlank(message)) {
                        return;
                    }
                    ((com.hzero.core.redis.handler.IQueueHandler) handler).process(message);
                }
            } else if (handler instanceof com.hzero.core.redis.handler.IBatchQueueHandler) {
                com.hzero.core.redis.handler.IBatchQueueHandler batchQueueHandler = (com.hzero.core.redis.handler.IBatchQueueHandler) handler;
                int size = batchQueueHandler.getSize();
                if (size <= 0) {
                    batchQueueHandler.process(redisQueueHelper.pullAll(key));
                } else {
                    while (true) {
                        List<String> list = redisQueueHelper.pullAll(key, size);
                        if (CollectionUtils.isEmpty(list)) {
                            return;
                        }
                        batchQueueHandler.process(list);
                    }
                }
            }
        }
    }
}
