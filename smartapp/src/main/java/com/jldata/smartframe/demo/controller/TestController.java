package com.jldata.smartframe.demo.controller;

import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.demo.model.CatalogEntity;
import com.jldata.smartframe.demo.repository.CatalogRepository;
import com.jldata.smartframe.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private TestService testService;


    @RequestMapping("test/redis")
    public RestResult list(){
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

    @RequestMapping("test/transaction1")
    @Transactional
    public RestResult transaction1(){
        System.out.println("测试是否jdbcTemplate 和  jpa 事务内共享数据源");
        String sql = "select count(1) from catalog" ;
        long count = jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println("之前数量"+count);
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setContent("内容哈哈");
        catalogEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        catalogEntity.setTitle("标题啊哈哈");
        catalogRepository.save(catalogEntity);
         count = jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println(count);
        return new RestResult("");
    }
    @RequestMapping("test/transaction2")
    @Transactional
    public RestResult transaction2(){
        System.out.println("测试是否jdbcTemplate 和  jpa 事务内是否回滚");
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setContent("内容哈哈1");
        catalogEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        catalogEntity.setTitle("标题啊哈哈1");
        catalogRepository.save(catalogEntity);
        String sql = "update catalog set content = '内容aaa' where id=1";
        jdbcTemplate.update(sql);
        if(true){
            throw new RuntimeException("故意的");
        }
        return new RestResult("");
    }
    @RequestMapping("test/required")
    public RestResult required(){
        testService.tran1();
        return new RestResult("");
    }
}
