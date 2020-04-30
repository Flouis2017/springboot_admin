package com.flouis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @description 是否存在key
     */
    public Boolean hasKey(String key) {
        if (null==key){
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * @description 删除key
     */
    public Boolean delete(String key) {
        if (null==key){
            return false;
        }
        return redisTemplate.delete(key);
    }

    /**
     * @description 批量删除key
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }


    /**
     * @description 设置过期时间
     */
    public Boolean expire(String key, Long timeout, TimeUnit timeUnit) {
        if (null == key || null == timeUnit){
            return false;
        }
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * @description 查找匹配的key
     */
    public Set<String> keys(String pattern) {
        if (null == pattern){
            return null;
        }
        return redisTemplate.keys(pattern);
    }


    /**
     * @description 持久化key，即将key过期时间设置为-1
     */
    public Boolean persist(String key) {
        if (null == key){
            return false;
        }
        return redisTemplate.persist(key);
    }

    /**
     * @description 返回key的剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        if (null == key || null == timeUnit){
            return null;
        }
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * @description 设置指定key的值，过期时间为-1
     */
    public void set(String key, Object value) {
        if (null == key || null == value){
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }
    /**
     * @description 设置key的值并设置过期时间
     */
    public void set(String key, Object value, Long time, TimeUnit timeUnit){
        if (null == key || null == value || null == timeUnit){
            return;
        }
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * @description 获取指定Key的Value
     */
    public Object get(String key){
        if (null == key){
            return null;
        }
        return  redisTemplate.opsForValue().get(key);
    }

    /**
     * @description 获取指定Key的Value
     */
    public String getString(String key){
        return String.valueOf(this.get(key));
    }

}
