package com.jldata.smartframe.demo;

import com.jldata.smartframe.base.TestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestArticleController extends TestRestBussinessBase {

    @Test
    public void testList() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("page","1");
        param.put("size","10");
        param.put("word","1");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/article/list");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testlistFilter() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("page","1");
        param.put("size","10");
        param.put("filter_CONTAINS_title","1");
        param.put("filter_CONTAINS_content","1");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/article/listFilter");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testlistJdbc() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("page","1");
        param.put("size","10");
        param.put("word","1");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/article/listJdbc");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testget() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(1)).post(""+PRE_PATH+"/article/get");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testdelete() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(2))
                .post(""+PRE_PATH+"/article/delete");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testupdate() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id","1");
        param.put("title","标题修改1");
        param.put("content","内容修改1");
        param.put("createDate","2018-01-01 00:00:00");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/article/update");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testadd() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id","111");
        param.put("title","标题111");
        param.put("content","内容111");
        param.put("createDate","2018-01-01 00:00:00");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/article/add");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testfindByTitleORContent() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("word","1");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/article/findByTitleORContent");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testqueryByTitleORContent() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("word","%1%");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/article/queryByTitleORContent");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testqueryObjectByTitleORContent() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("word","%1%");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token).body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/article/queryObjectByTitleORContent");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }

}
