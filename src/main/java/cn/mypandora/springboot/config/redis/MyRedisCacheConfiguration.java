package cn.mypandora.springboot.config.redis;

import java.time.Duration;
import java.util.StringJoiner;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Cache集成redis的运行原理： Spring缓存抽象模块通过CacheManager来创建、管理实际缓存组件，当SpringBoot应用程序引入spring-boot-starter-data-redis依赖后，
 * 容器中将注册的是CacheManager实例RedisCacheManager对象，RedisCacheManager来负责创建RedisCache作为缓存管理组件， 由RedisCache操作redis服务器实现缓存数据操作。
 *
 * @author hankaibo
 * @date 2020/5/7
 * @see <a href="https://www.cnblogs.com/ashleyboy/p/9595584.html">参考一</a>
 * @see <a href="https://juejin.im/post/5d7260026fb9a06af50feedf#heading-7">参考二</a>
 */
@Slf4j
@EnableCaching
@Configuration
public class MyRedisCacheConfiguration extends CachingConfigurerSupport {

    private static final String KEY_PREFIX = "CACHE:";
    private static final ObjectMapper OM = new ObjectMapper();

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringJoiner joiner = new StringJoiner(":", KEY_PREFIX, "");
            joiner.add(target.getClass().getSimpleName());
            joiner.add(method.getName());

            for (Object param : params) {
                try {
                    joiner.add(OM.writeValueAsString(param));
                } catch (JsonProcessingException e) {
                    log.error("缓存方法失败：" + target.getClass() + "#" + method.getName() + ":" + param);
                }
            }
            return joiner.toString();
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    /**
     * 自定义缓存管理实现，替换默认。
     *
     * @param factory
     *            LettuceConnection factory
     * @return 缓存管理器
     */
    @Bean
    @Primary
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            // 设置缓存的默认超时时间：30
            .entryTtl(Duration.ofMinutes(30L))
            // 如果空值，不缓存
            .disableCachingNullValues()
            // 设置key序列化器
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
            // 设置value序列化器
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
            .cacheDefaults(cacheConfiguration).build();
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
