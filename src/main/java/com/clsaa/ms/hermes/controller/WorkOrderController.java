package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.service.WorkOrderCommentService;
import com.clsaa.ms.hermes.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 任贵杰
 * @version v1
 * @summary 工单相关接口
 * @since 2018/4/29
 */
@CrossOrigin
@RestController
public class WorkOrderController {
  @Autowired
  private WorkOrderService workOrderService;
  @Autowired
  private WorkOrderCommentService workOrderCommentService;
}
