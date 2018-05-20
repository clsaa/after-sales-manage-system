package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.entity.dto.WorkOrderCommentDtoV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.service.WorkOrderCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author 任贵杰
 * @version v1
 * @summary 工单相关对话接口
 * @since 2018/4/29
 */
@CrossOrigin
@RestController
public class WorkOrderCommentController {

  @Autowired
  private WorkOrderCommentService workOrderCommentService;

  @PostMapping("/v1/workorder/{workOrderId}/comment")
  public Mono<String> addStaffCommentV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                        @PathVariable("workOrderId") String workOrderId,
                                        @RequestBody WorkOrderCommentDtoV1 workOrderDtoV1) {
    BizAssert.validParam(workOrderDtoV1 != null, BizCodes.INVALID_PARAM);
    BizAssert.validParam(workOrderDtoV1.getSource() != null, BizCodes.INVALID_PARAM);
    BizAssert.validParam(workOrderDtoV1.getSource() == 1 || workOrderDtoV1.getSource() == 2, BizCodes.INVALID_PARAM);
    BizAssert.validParam(workOrderDtoV1.getContent() != null
      && workOrderDtoV1.getContent().length() > 0
      && workOrderDtoV1.getContent().length() < 180, BizCodes.INVALID_PARAM.getCode(), "内容参数非法");
    //判断来源
    if (workOrderDtoV1.getSource() == WorkOrderCommentDtoV1.SOURCE_CUSTOMER) {
      return Mono.create(monoSink ->
        monoSink.success(this.workOrderCommentService.addCustomerComment(loginUserId, workOrderId, workOrderDtoV1.getContent())));
    } else if (workOrderDtoV1.getSource() == WorkOrderCommentDtoV1.SOURCE_STAFF) {
      return Mono.create(monoSink ->
        monoSink.success(this.workOrderCommentService.addStaffComment(loginUserId, workOrderId, workOrderDtoV1.getContent())));
    }
    BizAssert.justFailed(BizCodes.INVALID_PARAM);
    //不可达
    return null;
  }
}
