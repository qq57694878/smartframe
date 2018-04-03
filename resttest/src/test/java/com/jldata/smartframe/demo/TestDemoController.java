package com.jldata.smartframe.demo;

import com.jldata.smartframe.base.TestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestDemoController extends TestRestBussinessBase {

    @Test
    public void testList() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("pageNum","1");
        param.put("pageSize","10");
        param.put("word","");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/table/list");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
      /*  Response r = RestAssured.given(this.spec).contentType("application/json").body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/table/list");
        r.then().statusCode(200);
        r.prettyPrint();*/
    }

}
