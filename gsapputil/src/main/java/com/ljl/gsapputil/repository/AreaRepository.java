package com.ljl.gsapputil.repository;

import com.ljl.gsapputil.model.AreaCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaRepository extends JpaRepository<AreaCodeEntity, Integer>, JpaSpecificationExecutor<AreaCodeEntity> {
}
