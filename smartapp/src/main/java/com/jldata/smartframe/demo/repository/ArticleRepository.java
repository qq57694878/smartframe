package com.jldata.smartframe.demo.repository;


import com.jldata.smartframe.core.jpa.BaseRepository;
import com.jldata.smartframe.demo.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository  extends BaseRepository<Article, Long> {
    /**
     * 可通过Query来查询固定条件的，返回的是List内固定Entity
     * 可惜一点不能支持动态条件
     * @param title
     * @param content
     * @return
     */
    @Query(value = "SELECT * FROM article WHERE title like  ?1  or content like ?2", nativeQuery = true)
    List<Article> queryByTitleOrContent(String title, String content);

    /**
     * 查询不直接返回Entiry
     * @param title
     * @param content
     * @return
     */
    @Query(value = "SELECT id,length(title) as len  FROM article WHERE title like  ?1  or content like ?2", nativeQuery = true)
    List<Object[]> queryObjectByTitleOrContent(String title, String content);

    List<Article> findByTitleContainingOrContentContaining(String title, String content);
}
