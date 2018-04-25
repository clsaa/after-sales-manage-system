package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 顾客相关接口
 *
 * @author 任贵杰
 */
@RestController
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @ApiOperation("根据顾客id查询顾客信息")
  @GetMapping("/customer/{id}")
  public Mono<CustomerV1> getByIdV1(@PathVariable Long id) {
    return Mono.just(this.customerService.getCustomerV1ById(id));
  }
}
