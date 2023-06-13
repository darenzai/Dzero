package com.hzero.core.redis;

import com.hzero.core.redis.config.DynamicRedisTemplateFactory;
import com.hzero.core.redis.AbstractRoutingRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 动态 RedisTemplate ，以支持动态切换 redis database
 *
 * @author bojiangzhou 2018/08/28
 */
public class DynamicRedisTemplate<K, V> extends AbstractRoutingRedisTemplate<K, V> {

    private final DynamicRedisTemplateFactory<K, V> dynamicRedisTemplateFactory;

    public DynamicRedisTemplate(DynamicRedisTemplateFactory<K, V> dynamicRedisTemplateFactory) {
        this.dynamicRedisTemplateFactory = dynamicRedisTemplateFactory;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return com.hzero.core.redis.RedisDatabaseThreadLocal.get();
    }

    @Override
    protected RedisTemplate<K, V> createRedisTemplateOnMissing(Object lookupKey) {
        return dynamicRedisTemplateFactory.createRedisTemplate((Integer) lookupKey);
    }

}
