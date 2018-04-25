package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.dao.CustomerDao;
import com.clsaa.ms.hermes.entity.po.Customer;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RenGuiJie
 * @summary 考试相关服务类
 * @since 2018/4/26
 */
@Service
public class CustomerService {
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

  public CustomerV1 getCustomerV1ById(Long id) {
    Customer customer = this.customerDao.getById(id);
    return valueOf(customer);
  }
}
