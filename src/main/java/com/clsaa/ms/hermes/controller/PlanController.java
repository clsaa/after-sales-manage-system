package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 任贵杰
 * @version v1
 * @summary 计划接口
 * @since 2018/4/29
 */
@RestController
public class PlanController {
  @Autowired
  private PlanService planService;
}
