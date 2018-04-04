package com.jldata.smartframe.demo.service;


import com.jldata.smartframe.core.jdbc.JdbcPageKit;
import com.jldata.smartframe.core.jpa.PropertyFilter;
import com.jldata.smartframe.demo.model.Article;
import com.jldata.smartframe.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private JdbcPageKit jdbcPageKit;
    /**
     * jpa 原始形式实现查询列表
     * @param requestMap
     * @param pageRequest
     * @return
     */
    public Page<Article> findArticleByTitleJPA(Map<String,Object> requestMap, Pageable pageRequest){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable =  PageRequest.of(pageRequest.getPageNumber(),pageRequest.getPageSize(),sort);
        Page<Article> page  =articleRepository.findAll(new Specification<Article>() {
            final String word = (String)requestMap.get("word");
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates= new ArrayList<Predicate>();

                if(word!=null&&word.length()>0){
                    Predicate p1 = criteriaBuilder.like(root.get("title"),"%" + word + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("content"),"%" + word + "%");
                    Predicate p = criteriaBuilder.or(p1,p2);
                    predicates.add(p);
                }
             return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
        return page;
    }

    public Page<Article> findArticleByTitleJdbc(Map<String, Object> requestMap, PageRequest pageRequest) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql  = new StringBuilder("select * from article where 1=1 ");
        if(requestMap.get("word")!=null){
            String word = String.valueOf(requestMap.get("word"));
            sql.append("and (title like ? or content like ? )");
            params.add("%"+word+"%");
            params.add("%"+word+"%");
        }
        Page page = jdbcPageKit.paginate(pageRequest.getPageNumber(),pageRequest.getPageSize(),sql.toString(),params.toArray());
        return page;
    }

    public Page<Article> findArticleByTitleFilter(Map<String, Object> requestMap, PageRequest pageRequest) {
        List<PropertyFilter> propertyFilters = PropertyFilter.buildFromMap(requestMap);
        Page<Article> page =  articleRepository.findAll(propertyFilters,pageRequest);
        return page;
    }
}
