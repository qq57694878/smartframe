package com.jldata.smartframe.demo.controller;


import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.demo.model.Document;
import com.jldata.smartframe.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;


    private boolean isnotnull(String s){
        if(s==null||s.length()==0||"null".equals(s)){
            return false;
        }
        return true;
    }
    @RequestMapping("document/list")
    public RestResult listData(@RequestBody Map<String,String> requestMap){//
        int pageNum=1;
        int pageSize =10;
            String spageNum = String.valueOf(requestMap.get("pageNum"));
            String spageSize =String.valueOf(requestMap.get("pageSize"));
            if(isnotnull(spageNum)){
                pageNum = Integer.parseInt(spageNum);
            }
            if(isnotnull(spageSize)){
                pageSize =Integer.parseInt(spageSize);
            }
        pageNum=pageNum-1;
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);
        Page<Document> page = documentRepository.findAll(pageRequest);
         return new RestResult(page);
    }

}
