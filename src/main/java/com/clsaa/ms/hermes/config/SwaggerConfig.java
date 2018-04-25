//package com.clsaa.ms.hermes.config;
//
//import com.google.common.base.Predicate;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Bean;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * swagger2 配置类
// * @author 任贵杰
// */
//@SpringBootConfiguration
//@EnableSwagger2
//public class SwaggerConfig {
//
//  @Bean
//  public Docket adminApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//            .groupName("Admin API")
//            .forCodeGeneration(true)
//            .pathMapping("/")
//            .select()
//            .paths(this.paths())
//            .build()
//            .apiInfo(this.apiInfo())
//            .useDefaultResponseMessages(false);
//  }
//
//  private Predicate<String> paths() {
//    return PathSelectors.regex("^/(?!error).*$");
//  }
//
//  private ApiInfo apiInfo() {
//    Contact contact = new Contact("任贵杰", "https://github.com/clsaa/after-sales-manage-system.git", "812022339@qq.common");
//    return new ApiInfoBuilder()
//            .title("售后管理系统 Api")
//            .description("attendance-config-client service api")
//            .license("Apache License Version 2.0")
//            .contact(contact)
//            .version("1.0.0-SNAPSHOT")
//            .build();
//  }
//}
