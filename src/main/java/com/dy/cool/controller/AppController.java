package com.dy.cool.controller;

import com.dy.cool.redis.RedisHelperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ice
 * @Date 2019/9/20 14:21
 */
@RestController
public class AppController {


    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private RedisHelperImpl redisHelper;

    @Autowired
    private DefaultRedisScript defaultRedisScript;

   @GetMapping("testSentinel")
   public String testSentinel(HttpServletRequest request) {
       String value= (String) redisHelper.getValue("name");
       return value;
   }

    @GetMapping("testLua")
    public Long testLua(HttpServletRequest request) {
        List<String> keys=new ArrayList<>();
//        keys.add("spu");
        Long count= (Long) redisHelper.getRedisTemplate().execute(defaultRedisScript,keys,new Object[]{});
        if(count==0){
            System.out.println("库存不足，无法购买");
        }else if (count>0){
            System.out.println("库存数量为："+count);
        }else if(count<0){
            System.out.println("库存未初始化");
        }
        return count;
    }
}
