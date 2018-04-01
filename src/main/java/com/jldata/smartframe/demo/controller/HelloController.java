package com.jldata.smartframe.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/1/001.
 */
@RestController
public class HelloController {
    @RequestMapping("/say")
    public Object say(){
        return "i am leaning 3";
    }
}
