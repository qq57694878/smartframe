package com.jldata.smartframe.test.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

/**
 * 业务测试基类
 */
public class AigsTestRestBussinessBase extends AigsTestRestBase {
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected String token;
    protected RequestSpecification spec;
    public final String TOKEN ="token";


    private RestDocumentationFilter documentationFilter;
    // 执行测试方法之前初始化模拟request,response
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Before
    public void init() throws Exception {
        super.init();
        this.getToken();
        setUp();
    }




    public void getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
        param.put("username","admin");
        param.put("password", "123456");
        Response r = RestAssured.given().contentType("application/json").body(param)
                .post(""+PRE_PATH+"/wechat/bind/loginTest.json");
        r.then().statusCode(200).body("code", Matchers.equalTo("00000"));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        this.token =(String)m.get("token");
    }

    public void setUp() {
        final String token1 = this.token;
        this.documentationFilter = document("{ClassName}/{methodName}",
                preprocessRequest(),preprocessResponse(prettyPrint()));
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(this.restDocumentation)
                        .snippets().withEncoding("UTF-8")).addFilter(documentationFilter).setContentType(ContentType.JSON)
                .build();
        //this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(this.restDocumentation).snippets().withTemplateFormat(TemplateFormats.markdown())).build();
    }
    /*public void setUp() {
        final String token1 = this.token;
        this.documentationFilter = document("{className}/{methodName}",
                preprocessRequest(new OperationPreprocessorAdapter() {
                   private final OperationRequestFactory requestFactory = new OperationRequestFactory();
                    @Override
                    public OperationRequest preprocess(OperationRequest request) {
                        return requestFactory.createFrom(request, this.addHeaders(request.getHeaders()));
                    }
                    private HttpHeaders addHeaders(HttpHeaders originalHeaders) {
                        HttpHeaders processedHeaders = new HttpHeaders();
                        processedHeaders.putAll(originalHeaders);
                        if(!processedHeaders.containsKey(TOKEN)){
                            processedHeaders.add(TOKEN,token1);
                        }
                        return processedHeaders;
                    }
                }),preprocessResponse(new OperationPreprocessorAdapter() {
                    private final OperationResponseFactory responseFactory = new OperationResponseFactory();
                    private  ObjectMapper objectMapper = new ObjectMapper();
                    @Override
                    public OperationResponse preprocess(OperationResponse response){

                        byte[] modifiedContent = new byte[0];
                        try {
                            modifiedContent = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(objectMapper.readTree(response.getContent()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return this.responseFactory.createFrom(response, modifiedContent);
                    }
                }));
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(this.restDocumentation)
                        .snippets().withEncoding("UTF-8")).addFilter(documentationFilter).setContentType(ContentType.JSON)
                .build();

        //this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(this.restDocumentation).snippets().withTemplateFormat(TemplateFormats.markdown())).build();
    }*/

}
