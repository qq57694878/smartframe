package com.jldata.smartframe.demo.repository;


import com.jldata.smartframe.core.jpa.BaseRepository;
import com.jldata.smartframe.demo.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleRepository  extends BaseRepository<Article, Long> {
}
