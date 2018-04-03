package com.jldata.smartframe.demo.controller;

import com.jldata.smartframe.core.common.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("test/redis")
    public RestResult list( ){
        redisTemplate.opsForHash().put("user:","1","1");
       String value = (String) redisTemplate.opsForHash().get("user:","1");
        redisTemplate.opsForHash().delete("user:","1");
        value = (String) redisTemplate.opsForHash().get("user:","1");
        return new RestResult(value);
    }

    @RequestMapping("test/dialect")
    public RestResult dialect(){
        return new RestResult("");
    }
}
