package com.clsaa.ms.hermes.dao;

import com.clsaa.ms.hermes.entity.po.Customer;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任贵杰
 * @version v1
 * @summary 顾客DAO
 * @since 2018-04-29
 */
public interface CustomerDao {

  /**
   * 根据id查询顾客信息
   *
   * @param id 顾客id
   * @return {@link Customer}
   */
  Customer getById(String id);

  /**
   * 根据手机号查询顾客信息
   *
   * @param mobile 顾客手机号
   * @return {@link Customer}
   */
  Customer getByMobile(String mobile);

  /**
   * 插入顾客信息
   *
   * @param customer {@link Customer}
   * @return int 插入记录数
   */
  int add(Customer customer);

  /**
   * 更新顾客信息
   *
   * @param customer {@link Customer}
   * @return int 修改记录数
   */
  int update(Customer customer);

  /**
   * 查询分页数据总量
   *
   * @param type    类型
   * @param gender  性别
   * @param keyword 关键词
   * @return int 分页数据总量
   */
  int getPaginationCount(@Param("type") Integer type, @Param("gender") Integer gender, @Param("keyword") String keyword);

  /**
   * 查询分页数据
   *
   * @param type      类型
   * @param gender    性别
   * @param keyword   关键词
   * @param rowOffset 页偏移量
   * @param pageSize  页大小
   * @return {@link List<Customer>}
   */
  List<Customer> getPaginationList(Integer type, Integer gender, String keyword, int rowOffset, int pageSize);
}
