package com.jldata.smartframe.core.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository <T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	/**
	 * 按条件分页查询
	 * @param propertyFilters
	 * @param pageable
	 * @param <S>
	 * @return
	 */
    <S extends T> Page<S> findAll(List<PropertyFilter> propertyFilters, Pageable pageable);

}
