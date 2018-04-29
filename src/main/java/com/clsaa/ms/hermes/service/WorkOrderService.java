package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.dao.WordOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 任贵杰
 * @summary 工单服务
 * @since 2018/4/29
 */
@Service
public class WorkOrderService {
  @Autowired
  private WordOrderDao wordOrderDao;
}
