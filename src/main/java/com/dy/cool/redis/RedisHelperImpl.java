package com.dy.cool.redis;

import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by dingyang on 2018/11/21.
 */
@Component("redisHelper")
public class RedisHelperImpl <HK, T> implements RedisHelper<HK, T> {
    // 在构造器中获取redisTemplate实例, key(not hashKey) 默认使用String类型
    private RedisTemplate<String, T> redisTemplate;
    // 在构造器中通过redisTemplate的工厂方法实例化操作对象
    private HashOperations<String, HK, T> hashOperations;
    private ListOperations<String, T> listOperations;
    private ZSetOperations<String, T> zSetOperations;
    private SetOperations<String, T> setOperations;
    private ValueOperations<String, T> valueOperations;

    // IDEA虽然报错,但是依然可以注入成功, 实例化操作对象后就可以直接调用方法操作Redis数据库
    public RedisHelperImpl(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void hashPut(String key, HK hashKey, T domain) {
        hashOperations.put(key, hashKey, domain);
    }

    @Override
    public Map<HK, T> hashFindAll(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public T hashGet(String key, HK hashKey) {
        return hashOperations.get(key, hashKey);
    }

    @Override
    public void hashRemove(String key, HK hashKey) {
        hashOperations.delete(key, hashKey);
    }

    @Override
    public Long listPush(String key, T domain) {
        return listOperations.rightPush(key, domain);
    }

    @Override
    public Long listUnshift(String key, T domain) {
        return listOperations.leftPush(key, domain);
    }

    @Override
    public List<T> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }

    @Override
    public T listLPop(String key) {
        return listOperations.leftPop(key);
    }

    @Override
    public void valuePut(String key, T domain) {
        valueOperations.set(key, domain);
    }

    @Override
    public void valuePutExpire(String key, T domain, long timeOut) {
        valueOperations.set(key,domain,timeOut,TimeUnit.SECONDS);
    }

    @Override
    public T getValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean expirse(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }
    @Override
    public void flushDb(){
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public void publishMessage(String topic, T domain) {
        redisTemplate.convertAndSend(topic,domain);
    }

    @Override
    public Long increment(String key, Long size) {
        return valueOperations.increment(key,size);
    }

    public Set<String> getKeys(String pattern){
        return redisTemplate.keys(pattern);
    }
    public List<T> getValues(Set<String> keys){
        return valueOperations.multiGet(keys);
    }
    public Long removeKeys(Set<String> keys){
        return redisTemplate.delete(keys);
    }



    public ValueOperations<String, T> getValueOperations() {
        return valueOperations;
    }


    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public T excute(RedisScript<T> script, List<String> keys, Object... args){
        return redisTemplate.execute(script,keys,args);
    }
}
