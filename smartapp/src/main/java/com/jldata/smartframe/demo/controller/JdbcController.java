package com.jldata.smartframe.demo.controller;

import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.core.jdbc.JdbcPageKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class JdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcPageKit jdbcPageKit;

    @RequestMapping("jdbc/list")
    public RestResult list(@RequestBody Map<String,Object> requestMap){
       List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from article");
      return new RestResult(list);
    }
    @RequestMapping("jdbc/listpage")
    public RestResult listpage(@RequestBody Map<String,Object> requestMap){
        int pageNum=1;
        int pageSize =10;
        String spageNum = String.valueOf(requestMap.get("pageNum"));
        String spageSize =String.valueOf(requestMap.get("pageSize"));
        if(isnotnull(spageNum)){
            pageNum = Integer.parseInt(spageNum);
        }
        if(isnotnull(spageSize)){
            pageSize =Integer.parseInt(spageSize);
        }
        String sql  ="select * from article";
        Page page = jdbcPageKit.paginate(pageNum,pageSize,sql);
        return new RestResult(page);
    }

    private boolean isnotnull(String s){
        if(s==null||s.length()==0||"null".equals(s)){
            return false;
        }
        return true;
    }
}
