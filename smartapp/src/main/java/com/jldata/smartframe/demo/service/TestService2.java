package com.jldata.smartframe.demo.service;

import com.jldata.smartframe.demo.model.CatalogEntity;
import com.jldata.smartframe.demo.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class TestService2 {
    @Autowired
    private CatalogRepository catalogRepository;


    @Transactional(propagation = Propagation.NESTED)
    public void tran2(){
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setContent("内容哈哈");
        catalogEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        catalogEntity.setTitle("标题啊哈哈");
        catalogRepository.save(catalogEntity);
        if(true){
            throw new RuntimeException("1");
        }
    }
}
