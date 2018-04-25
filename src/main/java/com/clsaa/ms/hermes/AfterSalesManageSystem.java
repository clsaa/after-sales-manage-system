package com.clsaa.ms.hermes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author RenGuiJie
 * @summary 应用启动程序
 * @since 2018-04-22
 */
@SpringBootApplication
@MapperScan("com.clsaa.ms.hermes.dao")
@EnableAsync
@EnableConfigurationProperties
public class AfterSalesManageSystem {
  public static void main(String[] args) {
    SpringApplication.run(AfterSalesManageSystem.class, args);
  }
}
