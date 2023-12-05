package com.lengch.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lengchenghao
 * @create : 2023-11-07-10:20
 */
//@Slf4j
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {


    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${knife4j.enable}")
    private Boolean isEnable;


    @Bean
    public Docket apiDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(this.applicationName)
                        .description("")
                        .termsOfServiceUrl(null)
                        .contact(new Contact("冷呈昊","","lengchenghao@163.com"))
                        .version("1.0.0")
                        .build())
                .groupName("默认") //不能为空
                .enable(this.isEnable)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build().globalRequestParameters(this.getParameterList());
        return docket;
    }



    /**
     * 添加head参数配置
     */
    private List<RequestParameter> getParameterList() {
        RequestParameterBuilder clientIdTicket = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<>();
        clientIdTicket.name("Token").description("token令牌")
                .in(ParameterType.HEADER)
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());
        return pars;
    }
}
