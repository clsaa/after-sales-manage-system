package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.PlanDao;
import com.clsaa.ms.hermes.entity.po.Plan;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.entity.vo.PlanV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.BizCode;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 任贵杰
 * @summary 计划服务
 * @since 2018/4/29
 */
@Service
public class PlanService {
  @Autowired
  private PlanDao planDao;

  /**
   * 持久层对象转视图层对象
   *
   * @param plan {@link Plan}
   * @return {@link PlanV1}
   */
  private static PlanV1 valueOf(Plan plan) {
    if (plan == null) {
      return null;
    } else {
      PlanV1 planV1 = new PlanV1();
      BeanUtils.copyProperties(plan, planV1);
      long now = System.currentTimeMillis();
      if (now > planV1.getFinishTime().getTime() && planV1.getStatus() == Plan.STATUS_NO_FINISHED) {
        planV1.setStatus(Plan.STATUS_ALREADY_OLD);
      }
      return planV1;
    }
  }

  /**
   * 根据id查询计划
   *
   * @param id 计划id
   * @return {@link PlanV1}对象
   */
  public PlanV1 getById(String id) {
    return valueOf(this.planDao.getById(id));
  }

  /**
   * 添加计划
   *
   * @param loginUserId 登录用户id
   * @param type        计划类型
   * @param important   是否重要
   * @param urgent      是否紧急
   * @param note        备注
   * @param beginTime   计划开始时间
   * @param endTime     计划结束时间
   * @return 已添加计划的id
   */
  public String add(String loginUserId, Integer type, Boolean important, Boolean urgent, String note, Long beginTime, Long endTime) {
    Plan plan = new Plan(UUIDUtil.getUUID(), type, important, urgent, note,
      new Timestamp(beginTime), new Timestamp(endTime), loginUserId, loginUserId, Plan.STATUS_NO_FINISHED);
    int count = 0;
    try {
      count = this.planDao.add(plan);
    } catch (Exception e) {
      BizAssert.justFailed(BizCodes.ERROR_INSERT);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    return plan.getId();
  }

  /**
   * 删除计划(软删除)
   *
   * @param loginUserId 登录人id
   * @param id          计划id
   * @return void
   */
  public Void del(String loginUserId, String id) {
    Plan planExist = this.planDao.getById(id);
    BizAssert.found(planExist != null, BizCodes.INVALID_PARAM.getCode(), "要删除的计划不存在");
    BizAssert.found(planExist != null && planExist.getStatus() != Plan.STATUS_DEL, BizCodes.INVALID_PARAM.getCode(), "要删除的计划已是删除状态");
    int count = 0;
    try {
      count = this.planDao.updateStatus(id, Plan.STATUS_DEL, loginUserId);
    } catch (Exception e) {
      BizAssert.justFailed(BizCodes.ERROR_UPDATE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return Mono.empty().then().block();
  }

  /**
   * 完成计划
   *
   * @param loginUserId 登录人id
   * @param id          计划id
   * @return void
   */
  public Void finish(String loginUserId, String id) {
    Plan planExist = this.planDao.getById(id);
    BizAssert.found(planExist != null, BizCodes.INVALID_PARAM.getCode(), "要删除的计划不存在");
    BizAssert.found(planExist != null && planExist.getStatus() != Plan.STATUS_DEL, BizCodes.INVALID_PARAM.getCode(), "要删除的计划已是删除状态");
    int count = 0;
    try {
      count = this.planDao.updateStatus(id, Plan.STATUS_ALREADY_FINISHED, loginUserId);
    } catch (Exception e) {
      BizAssert.justFailed(BizCodes.ERROR_UPDATE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return Mono.empty().then().block();
  }

  public Pagination<PlanV1> getPagination(String loginUserId, Integer type, Integer important, Integer urgent, Integer status, Integer pageNo, Integer pageSize) {

    int count = this.planDao.getPaginationCount(loginUserId, type, important, urgent, status);

    Pagination<PlanV1> pagination = new Pagination<>();
    pagination.setPageNo(pageNo);
    pagination.setPageSize(pageSize);
    pagination.setTotalCount(count);
    if (count == 0) {
      pagination.setPageList(Collections.emptyList());
      return pagination;
    }

    List<Plan> planList = this.planDao.getPagination(loginUserId, type, important, urgent, status, pagination.getRowOffset(), pagination.getPageSize());
    List<PlanV1> planV1List = planList.stream().map(PlanService::valueOf).collect(Collectors.toList());
    pagination.setPageList(planV1List);
    return pagination;
  }
}
