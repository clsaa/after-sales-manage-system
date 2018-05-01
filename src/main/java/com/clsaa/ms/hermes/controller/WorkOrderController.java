package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.WorkOrderTypeEnum;
import com.clsaa.ms.hermes.entity.dto.CustomerDtoV1;
import com.clsaa.ms.hermes.entity.dto.WorkOrderCommentDtoV1;
import com.clsaa.ms.hermes.entity.dto.WorkOrderDtoV1;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.entity.vo.WorkOrderV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.service.WorkOrderCommentService;
import com.clsaa.ms.hermes.service.WorkOrderService;
import com.clsaa.ms.hermes.util.MobileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

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

  private void doValidation(String name, String mobile, Integer type, String title, String description) {
    BizAssert.validParam(name != null && name.length() > 0 && name.length() < 50, BizCodes.INVALID_PARAM.getCode(), "姓名非法");
    BizAssert.validParam(mobile != null && MobileValidator.isChinaMobile(mobile), BizCodes.INVALID_PARAM.getCode(), "手机号非法");
    BizAssert.validParam(type != null && WorkOrderTypeEnum.getByCode(type) != null, BizCodes.INVALID_PARAM.getCode(), "工单类型非法");
    BizAssert.validParam(title != null && title.length() > 0 && title.length() < 50, BizCodes.INVALID_PARAM.getCode(), "标题非法");
    BizAssert.validParam(description != null && description.length() > 0 && description.length() < 180, BizCodes.INVALID_PARAM.getCode(), "内容非法");
  }

  /**
   * 根据id查询工单信息
   *
   * @param id 工单id
   * @return {@link Mono<WorkOrderV1>}
   * @summary 根据id查询工单信息
   * @version v1
   * @author 任贵杰
   * @since 2018-05-01
   */
  @GetMapping("/v1/workorder/{id}")
  public Mono<WorkOrderV1> getByIdV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                     @PathVariable(value = "id") String id) {
    return Mono.create(monoSink -> monoSink.success(this.workOrderService.getWorkOrderV1ById(id)));
  }

  @GetMapping("/v1/workorder/pagination")
  public Mono<Pagination> getPaginationV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                          @RequestParam(value = "type", required = false) Integer type,
                                          @RequestParam(value = "status", required = false) Integer status,
                                          @RequestParam(value = "beginTime", required = false) Long beginTime,
                                          @RequestParam(value = "endTime", required = false) Long endTime,
                                          @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
    return Mono.create(monoSink ->
      monoSink.success(this.workOrderService.getPagination(loginUserId, type, status,
        beginTime == null ? null : new Timestamp(beginTime), endTime == null ? null : new Timestamp(endTime), pageNo, pageSize)));
  }

  @PutMapping("/v1/workorder/{id}/status")
  public Mono<Void> updateStatusV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                   @PathVariable(value = "id") String id,
                                   @RequestParam(value = "statusFrom") Integer statusFrom,
                                   @RequestParam(value = "statusTo") Integer statusTo) {
    return Mono.create(monoSink -> monoSink.success(this.workOrderService.updateStatus(loginUserId, id, statusFrom, statusTo)));
  }

  /**
   * 添加工单信息
   *
   * @param workOrderDtoV1 添加工单信息
   * @return 工单主键id
   * @summary 添加工单信息
   * @version v1
   * @author 任贵杰
   * @since 2018-05-01
   */
  @PostMapping("/v1/workorder")
  public Mono<String> addV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                            @RequestBody WorkOrderDtoV1 workOrderDtoV1) {
    this.doValidation(workOrderDtoV1.getName(), workOrderDtoV1.getMobile(),
      workOrderDtoV1.getType(), workOrderDtoV1.getTitle(), workOrderDtoV1.getDescription());
    return Mono.create(monoSink ->
      monoSink.success(this.workOrderService.add(workOrderDtoV1.getName(), workOrderDtoV1.getMobile(),
        workOrderDtoV1.getType(), workOrderDtoV1.getTitle(), workOrderDtoV1.getDescription())));
  }
}
