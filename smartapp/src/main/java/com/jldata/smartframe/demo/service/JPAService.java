package com.jldata.smartframe.demo.service;


import com.jldata.smartframe.core.jpa.PropertyFilter;
import com.jldata.smartframe.core.utils.StringUtils;
import com.jldata.smartframe.demo.model.Article;
import com.jldata.smartframe.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JPAService{
    @Autowired
    private ArticleRepository articleRepository;

    public Page<Article> selectArticleList(Map<String,Object>param, Pageable pageable) {
        Article article = new Article();
        article.setTitle(StringUtils.null2str((String)param.get("word")));
        article.setContent(StringUtils.null2str((String)param.get("word")));
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains()) //姓名采用“开始匹配”的方式查询
                .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("focus");  //忽略属性：是否关注。因为是基本类型，需要忽略掉
        //创建实例
        Example<Article> ex = Example.of(article, matcher);
        Sort sort = new Sort(Sort.Direction.ASC,"id");

        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
        return articleRepository.findAll(ex,pageRequest);
    }

    public Page<Article> selectArticleListSpecification(Map<String,Object>param, Pageable pageable) {
        final String cond = (String)param.get("word");
        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(cond!=null&&cond.length()>0){
                    Predicate p1 = criteriaBuilder.like(root.get("title"),"%" + cond + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("content"),"%" + cond + "%");
                    Predicate p = criteriaBuilder.or(p1,p2);
                    predicates.add(p);
                }
               return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
    }

    public Page<Article> selectArticleListFilter(List<PropertyFilter> propertyFilters, PageRequest pageRequest) {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(),pageRequest.getPageSize(),sort);
        return articleRepository.findAll(propertyFilters,pageable);
    }
}
