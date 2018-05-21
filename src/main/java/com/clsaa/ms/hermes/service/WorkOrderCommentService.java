package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.WordOrderCommentDao;
import com.clsaa.ms.hermes.dao.WorkOrderDao;
import com.clsaa.ms.hermes.entity.po.WorkOrder;
import com.clsaa.ms.hermes.entity.po.WorkOrderComment;
import com.clsaa.ms.hermes.entity.vo.WorkOrderCommentV1;
import com.clsaa.ms.hermes.entity.vo.WorkOrderV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 任贵杰
 * @summary 工单对话服务
 * @since 2018/4/29
 */
@Service
public class WorkOrderCommentService {
  @Autowired
  private WorkOrderService workOrderService;
  @Autowired
  private WordOrderCommentDao workOrderCommentDao;
  @Autowired
  private WorkOrderDao workOrderDao;

  private static WorkOrderCommentV1 valueOf(WorkOrderComment workOrderComment) {
    if (workOrderComment == null) {
      return null;
    } else {
      WorkOrderCommentV1 workOrderCommentV1 = new WorkOrderCommentV1();
      BeanUtils.copyProperties(workOrderComment, workOrderCommentV1);
      return workOrderCommentV1;
    }
  }

  /**
   * 根据工单id查询其全部对话列表
   *
   * @param workOrderId 工单id
   * @return {@link List<WorkOrderCommentV1>}
   */
  public List<WorkOrderCommentV1> getWorkOrderCommentV1ByWorkOrderId(String workOrderId) {
    return this.workOrderCommentDao.getByWorkOrderId(workOrderId)
      .stream()
      .map(WorkOrderCommentService::valueOf)
      .collect(Collectors.toList());
  }

  @Transactional(rollbackFor = Exception.class)
  public String addCustomerComment(String loginUserId, String workOrderId, String content) {
    WorkOrderV1 workOrderV1 = this.workOrderService.getWorkOrderV1ById(workOrderId);
    BizAssert.found(workOrderV1 != null, BizCodes.INVALID_PARAM.getCode(), "工单不存在");
    long staffCommentCount = this.getWorkOrderCommentV1ByWorkOrderId(workOrderId)
      .stream().filter(c -> StringUtils.isEmpty(c.getCustomerId())).count();
    if (staffCommentCount > 0) {
      this.workOrderService.updateStatus(loginUserId, workOrderV1.getId(), workOrderV1.getStatus(), WorkOrder.STATUS_PROCESSING);
    } else {
      this.workOrderService.updateStatus(loginUserId, workOrderV1.getId(), workOrderV1.getStatus(), WorkOrder.STATUS_PENDING);
    }
    String customerId = workOrderV1.getCustomerId();
    WorkOrderComment workOrderComment = new WorkOrderComment(UUIDUtil.getUUID(), workOrderId, customerId, "", content, customerId, customerId, WorkOrderComment.STATUS_OK);
    int count = 0;
    try {
      count = this.workOrderCommentDao.add(workOrderComment);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    return workOrderComment.getId();
  }

  public String addStaffComment(String loginUserId, String workOrderId, String content) {
    WorkOrderV1 workOrderV1 = this.workOrderService.getWorkOrderV1ById(workOrderId);
    BizAssert.found(workOrderV1 != null, BizCodes.INVALID_PARAM.getCode(), "工单不存在");
    this.workOrderService.updateStatus(loginUserId, workOrderV1.getId(), workOrderV1.getStatus(), WorkOrder.STATUS_WAIT_SUPPLEMENTAL);
    WorkOrderComment workOrderComment = new WorkOrderComment(UUIDUtil.getUUID(), workOrderId, "", loginUserId, content, loginUserId, loginUserId, WorkOrderComment.STATUS_OK);
    int count = 0;
    try {
      count = this.workOrderCommentDao.add(workOrderComment);
    } catch (Exception e) {
      e.printStackTrace();
      BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    return workOrderComment.getId();
  }
}
