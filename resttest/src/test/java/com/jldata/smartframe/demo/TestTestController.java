package com.jldata.smartframe.demo;

import com.jldata.smartframe.base.TestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestTestController extends TestRestBussinessBase {

    @Test
    public void testRedis() throws IOException {

        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .post(""+PRE_PATH+"/test/redis");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
      /*  Response r = RestAssured.given(this.spec).contentType("application/json").body(objectMapper.writeValueAsString(param))
                .post(""+PRE_PATH+"/table/list");
        r.then().statusCode(200);
        r.prettyPrint();*/
    }
    @Test
    public void testDialect() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .post(""+PRE_PATH+"/test/dialect");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();

    }
    @Test
    public void testTransaction1() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .post(""+PRE_PATH+"/test/transaction1");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testTransaction2() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .post(""+PRE_PATH+"/test/transaction2");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(500));
        r.prettyPrint();
    }
    @Test
    public void testrequired() throws IOException {
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .post(""+PRE_PATH+"/test/required");
        r.then().statusCode(200).body("errcode", Matchers.equalTo(500));
        r.prettyPrint();
    }
}
