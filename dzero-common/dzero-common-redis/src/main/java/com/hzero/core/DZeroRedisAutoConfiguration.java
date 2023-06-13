package com.hzero.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hzero.core.redis.DynamicRedisHelper;
import com.hzero.core.redis.DynamicRedisTemplate;
import com.hzero.core.redis.DZeroRedisProperties;
import com.hzero.core.redis.RedisHelper;
import com.hzero.core.redis.RedisQueueHelper;
import com.hzero.core.redis.config.DynamicRedisTemplateFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.hzero.core.redis.handler.HandlerInit;

/**
 * Redis配置类
 *
 */
@Configuration
@EnableConfigurationProperties({
        CacheProperties.class,
        DZeroRedisProperties.class
})
@ConditionalOnClass(name = {"org.springframework.data.redis.connection.RedisConnectionFactory"})
public class DZeroRedisAutoConfiguration {

    /**
     * @return StringRedisTemplate
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        buildRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * @return StringRedisTemplate
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        buildRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    private void buildRedisTemplate(RedisTemplate<String, String> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setStringSerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
    }

    /**
     * @return Hash 处理类
     */
    @Bean
    public HashOperations<String, String, String> hashOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * @return String 处理类
     */
    @Bean
    public ValueOperations<String, String> valueOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * @return List 处理类
     */
    @Bean
    public ListOperations<String, String> listOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * @return Set 处理类
     */
    @Bean
    public SetOperations<String, String> setOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * @return ZSet 处理类
     */
    @Bean
    public ZSetOperations<String, String> zSetOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    /**
     * 普通 RedisHelper
     */
    @Bean
    @ConditionalOnProperty(prefix = DZeroRedisProperties.PREFIX, name = "dynamic-database", havingValue = "false")
    public RedisHelper redisHelper(DZeroRedisProperties redisProperties) {
        return new RedisHelper();
    }

    /**
     * 动态 RedisHelper 配置
     */
    @Configuration
    @ConditionalOnProperty(prefix = DZeroRedisProperties.PREFIX, name = "dynamic-database", havingValue = "true", matchIfMissing = true)
    protected static class DynamicRedisAutoConfiguration {

        @Bean
        public RedisHelper redisHelper(StringRedisTemplate redisTemplate,
                                       RedisProperties redisProperties,
                                       ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
                                       ObjectProvider<RedisClusterConfiguration> clusterConfiguration,
                                       ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> jedisBuilderCustomizers,
                                       ObjectProvider<List<LettuceClientConfigurationBuilderCustomizer>> lettuceBuilderCustomizers) {

            DynamicRedisTemplateFactory<String, String> dynamicRedisTemplateFactory = new DynamicRedisTemplateFactory<>(
                    redisProperties,
                    sentinelConfiguration,
                    clusterConfiguration,
                    jedisBuilderCustomizers,
                    lettuceBuilderCustomizers
            );

            DynamicRedisTemplate<String, String> dynamicRedisTemplate = new DynamicRedisTemplate<>(dynamicRedisTemplateFactory);
            dynamicRedisTemplate.setDefaultRedisTemplate(redisTemplate);
            Map<Object, RedisTemplate<String, String>> map = new HashMap<>(8);
            map.put(redisProperties.getDatabase(), redisTemplate);
            dynamicRedisTemplate.setRedisTemplates(map);

            return new DynamicRedisHelper(dynamicRedisTemplate);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisQueueHelper redisQueueHelper(RedisHelper redisHelper, DZeroRedisProperties redisProperties) {
        return new RedisQueueHelper(redisHelper, redisProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public HandlerInit handlerInit(RedisQueueHelper redisQueueHelper, DZeroRedisProperties redisProperties) {
        return new HandlerInit(redisQueueHelper, redisProperties);
    }
}
