package com.jldata.smartframe.test.base;


import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import org.junit.Before;

/**
 * Created by jinliang on 2016/8/23.
 */
public class AigsTestRestBase {

    public final static String PRE_PATH="/aigs-wechat";


    @Before
    public void init() throws Exception {
        //指定 URL 和端口号
        RestAssured.baseURI ="http://localhost";
        RestAssured.port = 80;
        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8").defaultQueryParameterCharset("UTF-8"));
        RestAssured.config = RestAssured.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8","multipart/form-data"));
    }




}
