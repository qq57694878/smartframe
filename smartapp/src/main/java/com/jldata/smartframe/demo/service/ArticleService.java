package com.jldata.smartframe.demo.service;


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

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    public Page<Article> findArticleByTitle(String word, Pageable pageRequest){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(),pageRequest.getPageSize(),sort);
/*        return articleRepository.findAll(pageable);*/
        Page<Article> page  =articleRepository.findAll(new Specification<Article>() {
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
}
