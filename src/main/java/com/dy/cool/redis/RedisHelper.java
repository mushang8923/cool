package com.dy.cool.redis;



import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by dingyang on 2018/11/21.
 */
public interface RedisHelper<HK, T>  {
    /**
     * Hash结构 添加元素 * @param key key * @param hashKey hashKey * @param domain 元素
     */
    void hashPut(String key, HK hashKey, T domain);

    /**
     * Hash结构 获取指定key所有键值对 * @param key * @return
     */
    Map<HK, T> hashFindAll(String key);

    /**
     * Hash结构 获取单个元素 * @param key * @param hashKey * @return
     */
    T hashGet(String key, HK hashKey);

    void hashRemove(String key, HK hashKey);

    /**
     * List结构 向尾部(Right)添加元素 * @param key * @param domain * @return
     */
    Long listPush(String key, T domain);

    /**
     * List结构 向头部(Left)添加元素 * @param key * @param domain * @return
     */
    Long listUnshift(String key, T domain);

    /**
     * List结构 获取所有元素 * @param key * @return
     */
    List<T> listFindAll(String key);

    /**
     * List结构 移除并获取数组第一个元素 * @param key * @return
     */
    T listLPop(String key);

    /**
     * 对象的实体类
     * @param key
     * @param domain
     * @return
     */
    void valuePut(String key, T domain);

    /**
     * 设置过期时间
     * @param key
     * @param domain
     * @param timeOut
     */
    void valuePutExpire(String key, T domain, long timeOut);

    /**
     * 获取对象实体类
     * @param key
     * @return
     */
    T getValue(String key);

    void remove(String key);

    /**
     * 设置过期时间 * @param key 键 * @param timeout 时间 * @param timeUnit 时间单位
     */
    boolean expirse(String key, long timeout, TimeUnit timeUnit);

    void flushDb();

    /**
     * 发布订阅消息
     * @param topic
     * @param domain
     */
    void publishMessage(String topic, T domain);

    /**
     * 对应计数 + 步长
     * @param key
     * @param size
     * @return
     */
    Long increment(String key, Long size);


    /**
     * 模糊查询
     * @param pattern
     * @return
     */
    Set<String> getKeys(String pattern);

    /**
     * 批量获取
     * @param keys
     * @return
     */
    List<T> getValues(Set<String> keys);

    /**
     * 批量删除
     * @param keys
     * @return
     */
    Long removeKeys(Set<String> keys);

    /**
     * 执行lua脚本
     * @param script
     * @param keys
     * @param args
     * @return
     */
   T excute(RedisScript<T> script, List<String> keys, Object... args);

    /**
     * 获取template
     * @return
     */
    RedisTemplate<String, T> getRedisTemplate();
}
