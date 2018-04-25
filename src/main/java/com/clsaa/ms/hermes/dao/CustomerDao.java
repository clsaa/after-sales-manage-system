package com.clsaa.ms.hermes.dao;

import com.clsaa.ms.hermes.entity.po.Customer;

/**
 * @author 任贵杰
 * @version v1
 * @summary 视频迁移DAO
 * @since 2018-04-25
 */
public interface CustomerDao {

  /**
   * 根据id查询顾客信息
   *
   * @param id 顾客id
   * @return {@link Customer}
   */
  Customer getById(Long id);
}
