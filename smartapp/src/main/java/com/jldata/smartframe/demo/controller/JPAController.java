package com.jldata.smartframe.demo.controller;

import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.core.jpa.PropertyFilter;
import com.jldata.smartframe.core.utils.StringUtils;
import com.jldata.smartframe.demo.model.Article;
import com.jldata.smartframe.demo.service.JPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jpa")
public class JPAController {
    @Autowired
    private JPAService jpaService;
    @RequestMapping("/list")
    public RestResult list(@RequestBody Map<String,Object> jpaRequest){
        int pageNum=1;
        int pageSize =10;
        String spageNum = String.valueOf(jpaRequest.get("page"));
        String spageSize =String.valueOf(jpaRequest.get("size"));
        if(!StringUtils.isEmpty(spageNum)){
            pageNum = Integer.parseInt(spageNum);
        }
        if(!StringUtils.isEmpty(spageSize)){
            pageSize =Integer.parseInt(spageSize);
        }
        pageNum=pageNum-1;
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        PageRequest pageRequest = PageRequest.of(pageNum,pageSize,sort);
        Page<Article> page =  jpaService.selectArticleListSpecification(jpaRequest,pageRequest);
        return new RestResult(page);
    }

    @RequestMapping("/listFilter")
    public RestResult listFilter(@RequestBody Map<String,Object> jpaRequest){
        int pageNum=1;
        int pageSize =10;
        String spageNum = String.valueOf(jpaRequest.get("page"));
        String spageSize =String.valueOf(jpaRequest.get("size"));
        if(!StringUtils.isEmpty(spageNum)){
            pageNum = Integer.parseInt(spageNum);
        }
        if(!StringUtils.isEmpty(spageSize)){
            pageSize =Integer.parseInt(spageSize);
        }
        pageNum=pageNum-1;
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        PageRequest pageRequest = PageRequest.of(pageNum,pageSize,sort);
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(jpaRequest);
        Page<Article> page =  jpaService.selectArticleListFilter(propertyFilters,pageRequest);
        return new RestResult(page);
    }
}
