package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.PlanTypeEnum;
import com.clsaa.ms.hermes.entity.dto.PlanDtoV1;
import com.clsaa.ms.hermes.entity.vo.PlanV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.BizCode;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author 任贵杰
 * @version v1
 * @summary 计划接口
 * @since 2018/4/29
 */
@CrossOrigin
@RestController
public class PlanController {
  @Autowired
  private PlanService planService;

  private void doValidation(String loginUserId, Integer type, Boolean important, Boolean urgent, String note, Long beginTime, Long endTime) {
    BizAssert.validParam(loginUserId != null, BizCodes.INVALID_PARAM.getCode(), "登录用户id错误");
    BizAssert.validParam(type != null && PlanTypeEnum.getByCode(type) != null, BizCodes.INVALID_PARAM.getCode(), "计划类型参数非法");
    BizAssert.validParam(important != null, BizCodes.INVALID_PARAM.getCode(), "重要性参数非法");
    BizAssert.validParam(urgent != null, BizCodes.INVALID_PARAM.getCode(), "紧急程度参数非法");
    BizAssert.validParam(note != null && note.length() < 180, BizCodes.INVALID_PARAM.getCode(), "备注参数非法");
    BizAssert.validParam(beginTime / 1000 < endTime / 1000, BizCodes.INVALID_PARAM.getCode(), "开始时间应晚于结束时间");
    BizAssert.validParam(endTime / 1000 > System.currentTimeMillis() / 1000, BizCodes.INVALID_PARAM.getCode(), "结束时间应大于当前时间");
  }

  /**
   * 根据id查询计划详细信息
   *
   * @param loginUserId 登录用户id
   * @param id          计划id
   * @return {@link Mono<PlanV1>}
   * @summary 查询计划信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @GetMapping("/v1/plan/{id}")
  public Mono<PlanV1> getByIdV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                @PathVariable(value = "id") String id) {
    return Mono.create(planV1MonoSink -> planV1MonoSink.success(this.planService.getById(id)));
  }

  /**
   * 添加计划信息,若成功返回计划id
   *
   * @param loginUserId 登录用户id
   * @param planDtoV1   {@link PlanDtoV1}
   * @return 添加的计划id
   * @summary 添加计划信息
   * @version v1
   * @author 任贵杰
   * @since 2018-05-01
   */
  @PostMapping("/v1/plan")
  public Mono<String> addV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                            @RequestBody PlanDtoV1 planDtoV1) {
    return Mono.create(stringMonoSink -> {
      this.doValidation(loginUserId, planDtoV1.getType(), planDtoV1.getImportant(), planDtoV1.getUrgent(),
        planDtoV1.getNote(), planDtoV1.getBeginTime(), planDtoV1.getEndTime());
      stringMonoSink.success(this.planService.add(loginUserId, planDtoV1.getType(), planDtoV1.getImportant(), planDtoV1.getUrgent(),
        planDtoV1.getNote(), planDtoV1.getBeginTime(), planDtoV1.getEndTime()));
    });
  }

  /**
   * 根据id删除计划
   *
   * @param loginUserId 登录用户id
   * @param id          计划id
   * @return {@link Mono<Void>}
   * @summary 删除计划
   * @version v1
   * @author 任贵杰
   * @since 2018-05-01
   */
  @DeleteMapping("/v1/plan/{id}")
  public Mono<Void> delV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                          @PathVariable(value = "id") String id) {
    return Mono.create(monoSink -> monoSink.success(this.planService.del(loginUserId, id)));
  }

  /**
   * 根据id完成计划
   *
   * @param loginUserId 登录用户id
   * @param id          计划id
   * @return {@link Mono<Void>}
   * @summary 完成计划
   * @version v1
   * @author 任贵杰
   * @since 2018-05-01
   */
  @PutMapping("/v1/plan/{id}")
  public Mono<Void> finishV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                             @PathVariable(value = "id") String id) {
    return Mono.create(monoSink -> monoSink.success(this.planService.finish(loginUserId, id)));
  }

  @GetMapping("/v1/plan/pagination")
  public Mono<Pagination> getPaginationV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                          @RequestParam(value = "type", required = false) Integer type,
                                          @RequestParam(value = "important", required = false) Integer important,
                                          @RequestParam(value = "urgent", required = false) Integer urgent,
                                          @RequestParam(value = "status", required = false) Integer status,
                                          @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
    return Mono.create(monoSink -> monoSink.success(this.planService.getPagination(loginUserId, type, important, urgent, status, pageNo, pageSize)));
  }

}
