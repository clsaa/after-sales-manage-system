package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.dao.PlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 任贵杰
 * @summary 计划服务
 * @since 2018/4/29
 */
@Service
public class PlanService {
  @Autowired
  private PlanDao planDao;
}
