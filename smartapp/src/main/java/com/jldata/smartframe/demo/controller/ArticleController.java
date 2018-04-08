package com.jldata.smartframe.demo.controller;


import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.core.mapper.BeanMapperKit;
import com.jldata.smartframe.demo.model.Article;
import com.jldata.smartframe.demo.repository.ArticleRepository;
import com.jldata.smartframe.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;


    private boolean isnotnull(String s){
        if(s==null||s.length()==0||"null".equals(s)){
            return false;
        }
        return true;
    }

    /**
     * jpa 原始方式实现分页查询列表
     * @param requestMap
     * @return
     */
    @RequestMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult listData(@RequestBody Map<String,Object> requestMap){
        int pageNum=1;
        int pageSize =10;
            String spageNum = String.valueOf(requestMap.get("page"));
            String spageSize =String.valueOf(requestMap.get("size"));
            if(isnotnull(spageNum)){
                pageNum = Integer.parseInt(spageNum);
            }
            if(isnotnull(spageSize)){
                pageSize =Integer.parseInt(spageSize);
            }
        pageNum=pageNum-1;
        PageRequest pageRequest = PageRequest.of(pageNum,pageSize);
        Page<Article> page = articleService.findArticleByTitleJPA(requestMap,pageRequest);
         return new RestResult(page);
    }

    /**
     * jpa封装的形式实现查询列表
     * @param requestMap
     * @return
     */
    @RequestMapping("/listFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult listFilter(@RequestBody Map<String,Object> requestMap){
        int pageNum=1;
        int pageSize =10;
        String spageNum = String.valueOf(requestMap.get("page"));
        String spageSize =String.valueOf(requestMap.get("size"));
        if(isnotnull(spageNum)){
            pageNum = Integer.parseInt(spageNum);
        }
        if(isnotnull(spageSize)){
            pageSize =Integer.parseInt(spageSize);
        }
        pageNum=pageNum-1;
        PageRequest pageRequest = PageRequest.of(pageNum,pageSize);
        Page<Article> page = articleService.findArticleByTitleFilter(requestMap,pageRequest);
        return new RestResult(page);
    }

    /**
     * jdbcTemplate方式实现查询列表
     * @param requestMap
     * @return
     */
    @RequestMapping("/listJdbc")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult listJdbc(@RequestBody Map<String,Object> requestMap){
        int pageNum=0;
        int pageSize =10;
        String spageNum = String.valueOf(requestMap.get("page"));
        String spageSize =String.valueOf(requestMap.get("size"));
        if(isnotnull(spageNum)){
            pageNum = Integer.parseInt(spageNum);
        }
        if(isnotnull(spageSize)){
            pageSize =Integer.parseInt(spageSize);
        }
        pageNum=pageNum-1;
        PageRequest pageRequest = PageRequest.of(pageNum,pageSize);
        Page<Article> page = articleService.findArticleByTitleJdbc(requestMap,pageRequest);
        return new RestResult(page);
    }
    @RequestMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult findOne(@RequestBody Long id){
        Article article = articleRepository.findById(id).get();
        return new RestResult(article);
    }
    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult delete(@RequestBody Long id){
        articleRepository.deleteById(id);
        return new RestResult("");
    }
    @RequestMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult update(@RequestBody Article article){
        Article o = articleRepository.findById(article.getId()).get();
        BeanMapperKit.copy(article,o);
        articleRepository.saveAndFlush(o);
        return new RestResult(o);
    }
    @RequestMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public RestResult add(@RequestBody Article article){
        article.setCreateDate(new Date());
        articleRepository.saveAndFlush(article);
        return new RestResult(article);
    }

    /**
     * jpa默认方法名规范查询
     * @param requestMap
     * @return
     */
    @RequestMapping("/findByTitleORContent")
    public RestResult findByTitleOrContent(@RequestBody Map<String,Object> requestMap){
        String word = (String)requestMap.get("word");
        List<Article> list =  articleRepository.findByTitleContainingOrContentContaining(word,word);
        return new RestResult(list);
    }
    /**
     * 通过Query注解执行sql
     * @param requestMap
     * @return
     */
    @RequestMapping("/queryByTitleORContent")
    public RestResult queryByTitleORContent(@RequestBody Map<String,Object> requestMap){
        String word = (String)requestMap.get("word");
        List<Article> list =    articleRepository.queryByTitleOrContent(word,word);
        return new RestResult(list);
    }
    /**
     * 通过Query注解执行统计类sql
     * @param requestMap
     * @return
     */
    @RequestMapping("/queryObjectByTitleORContent")
    public RestResult queryObjectByTitleORContent(@RequestBody Map<String,Object> requestMap){
        String word = (String)requestMap.get("word");
        List<Object[]> list =   articleRepository.queryObjectByTitleOrContent(word,word);
        return new RestResult(list);
    }
}
