package com.jldata.smartframe.test.aigs;

import com.jldata.smartframe.test.base.AigsTestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMyController extends AigsTestRestBussinessBase {
    /**
     * 查询完善店铺信息任务
     * @throws IOException
     */
    @Test
    public void selectStoreTask() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();

        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/wechat/storeTask/selectStoreTask.json");
        r.then().statusCode(200).body("code", Matchers.equalTo("00000"));
        r.prettyPrint();
    }
    /**
     * 提交完善店铺信息任务
     * @throws IOException
     */
    @Test
    public void commitStoreTask() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("storeCode","1111");
        param.put("storeArea","40");
        param.put("storeName","店铺名称");
        param.put("personNum","100");
        param.put("areaCode","110111");
        param.put("detailAddr","详细地址2");
        param.put("longitude","2222");
        param.put("latitude","3333");
        List<String> imgList = new ArrayList<String>();
        imgList.add("http://xxx/a.jpg");
        imgList.add("http://xxx/c.jpg");
        imgList.add("http://xxx/b.jpg");
        param.put("imgList",imgList);

        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/wechat/storeTask/commitStoreTask.json");
        r.then().statusCode(200).body("code", Matchers.equalTo("00000"));
        r.prettyPrint();
    }

    /**
     *  查询我的任务列表
     * @throws IOException
     */
    @Test
    public void getMyTaskList() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("pageSize","10");
        param.put("pageStart","0");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/wechat/myTask/getMyTaskList.json");
        r.then().statusCode(200).body("code", Matchers.equalTo("00000"));
        r.prettyPrint();
    }
    /**
     *  查询我的任务自定义任务完善
     * @throws IOException
     */
    @Test
    public void getCustomTaskFinishedInfo() throws IOException {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("taskId","2");
        Response r = RestAssured.given(this.spec).contentType("application/json").header(TOKEN,this.token)
                .body(objectMapper.writeValueAsString(param)).post(""+PRE_PATH+"/wechat/myTask/getCustomTaskFinishedInfo.json");
        r.then().statusCode(200).body("code", Matchers.equalTo("00000"));
        r.prettyPrint();
    }

}
