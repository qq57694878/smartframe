package com.jldata.smartframe.demo.controller;

import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.core.jpa.PropertyFilter;
import com.jldata.smartframe.demo.model.Article;
import com.jldata.smartframe.demo.service.JPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public RestResult list(@RequestBody Map<String,Object> jpaRequest, @ModelAttribute PageRequest pageRequest){
        Page<Article> page =  jpaService.selectArticleList(jpaRequest,pageRequest);
        return new RestResult(page);
    }

    @RequestMapping("/listFilter")
    public RestResult listFilter(@RequestBody Map<String,Object> demoRequest){
        PageRequest pageRequest =  PageRequest.of(Integer.parseInt(String.valueOf(demoRequest.get("pageNum"))),Integer.parseInt(String.valueOf(demoRequest.get("pageSize"))));
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(demoRequest);
        Page<Article> page =  jpaService.selectArticleListFilter(propertyFilters,pageRequest);
        return new RestResult(page);
    }
}
