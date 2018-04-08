package com.jldata.smartframe.demo.service;

import com.jldata.smartframe.demo.model.CatalogEntity;
import com.jldata.smartframe.demo.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class TestService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private TestService2 testService2;

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT)
    public void tran1(){
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setContent("内容哈哈1");
        catalogEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        catalogEntity.setTitle("标题啊哈哈1");
        catalogRepository.save(catalogEntity);
        try{
            testService2.tran2();
        }catch (Exception e){}

       //testService2.tran2();

    }

}
