package com.jldata.smartframe.base;


import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import org.junit.Before;

/**
 * Created by jinliang on 2016/8/23.
 */
public class TestRestBase {

    public final static String PRE_PATH="/api";


    @Before
    public void init() throws Exception {
        //指定 URL 和端口号
        RestAssured.baseURI ="http://localhost";
        RestAssured.port = 8080;
        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8").defaultQueryParameterCharset("UTF-8"));
        RestAssured.config = RestAssured.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultCharsetForContentType("UTF-8","multipart/form-data"));
    }




}
