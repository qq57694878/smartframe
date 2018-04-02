package com.jldata.smartframe.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/24.
 */
public class TestRestBussinessBase extends TestRestBase{
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String token;



    // 执行测试方法之前初始化模拟request,response
    @Before
    public void init() throws Exception{
        super.init();
        this.getToken();
    }

    public void getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
        param.put("username","admin");
        param.put("password", "123456");

        Response r = RestAssured.given().contentType("application/json").body(param).post(""+PRE_PATH+"/login.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        this.token =(String)m.get("token");
    }
}
