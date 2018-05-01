package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.CustomerDao;
import com.clsaa.ms.hermes.entity.po.Customer;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 任贵杰
 * @summary 顾客服务
 * @since 2018/4/26
 */
@Service
public class CustomerService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  @Autowired
  private CustomerDao customerDao;

  private static CustomerV1 valueOf(Customer customer) {
    if (customer == null) {
      return null;
    }
    CustomerV1 customerV1 = new CustomerV1();
    BeanUtils.copyProperties(customer, customerV1);
    return customerV1;
  }

  public Mono<CustomerV1> getCustomerV1ById(String id) {
    return Mono.justOrEmpty(this.customerDao.getById(id)).map(CustomerService::valueOf);
  }

  public Mono<CustomerV1> getCustomerV1ByMobile(String mobile) {
    return Mono.justOrEmpty(this.customerDao.getByMobile(mobile)).map(CustomerService::valueOf);
  }

  public Mono<String> add(String loginUserId, Integer type, String name, Integer age, Long birthday, Integer gender,
                          String mobile, String email, String wechat, String qq, String note) {
    Customer customer = new Customer(UUIDUtil.getUUID(), type, name, age, new Date(birthday), gender, mobile, email,
      wechat, qq, note, loginUserId, loginUserId, Customer.STATUS_OK);
    int count = 0;
    try {
       count = this.customerDao.add(customer);
    }catch (Exception e){
      BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    return Mono.just(customer.getId());
  }


  public Mono<Void> update(String id, String loginUserId, Integer type, String name, Integer age, Long birthday, Integer gender,
                           String mobile, String email, String wechat, String qq, String note) {
    Customer customerExist = this.customerDao.getById(id);
    BizAssert.found(customerExist != null, BizCodes.INVALID_PARAM.getCode(), "非法id,资源不存在");
    BizAssert.found(customerExist != null && customerExist.getStatus() != Customer.STATUS_DEL,
      BizCodes.INVALID_PARAM.getCode(), "顾客已被删除");

    int count = this.customerDao.update(new Customer(id, type, name, age, new Date(birthday), gender, mobile, email,
      wechat, qq, note, loginUserId, loginUserId, Customer.STATUS_OK));
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);

    return Mono.empty().then();
  }

  public Mono<Pagination<CustomerV1>> getPagination(String loginUserId, Integer type, Integer gender, String keyword, Integer pageNo, Integer pageSize) {

    int count = this.customerDao.getPaginationCount(type, gender, keyword);

    Pagination<CustomerV1> pagination = new Pagination<>();
    pagination.setPageNo(pageNo);
    pagination.setPageSize(pageSize);
    pagination.setTotalCount(count);
    if (count == 0) {
      pagination.setPageList(Collections.emptyList());
      return Mono.just(pagination);
    }
    List<CustomerV1> customerV1List = this.customerDao
      .getPaginationList(type, gender, keyword, pagination.getRowOffset(), pagination.getPageSize())
      .stream().map(CustomerService::valueOf).collect(Collectors.toList());

    pagination.setPageList(customerV1List);
    return Mono.just(pagination);
  }

  public Mono<Void> delById(String id) {
    Customer customerExist = this.customerDao.getById(id);
    BizAssert.found(customerExist != null, BizCodes.INVALID_PARAM.getCode(), "非法id,资源不存在");
    BizAssert.found(customerExist != null && customerExist.getStatus() != Customer.STATUS_DEL,
      BizCodes.INVALID_PARAM.getCode(), "顾客已被删除");
    customerExist.setStatus(Customer.STATUS_DEL);
    int count = this.customerDao.update(customerExist);
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return Mono.empty().then();
  }


  public Mono<Void> delBatchByIds(String[] ids) {
    //暂不处理异常情况
    return Flux.just(ids).map(id -> {
      Customer customerExist = this.customerDao.getById(id);
      if (customerExist != null){
        customerExist.setStatus(Customer.STATUS_DEL);
        this.customerDao.update(customerExist);
      }
      return id;
    }).subscribeOn(Schedulers.elastic()).collectList().then();
  }
}
