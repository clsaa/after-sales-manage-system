package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.WordOrderCommentDao;
import com.clsaa.ms.hermes.dao.WorkOrderDao;
import com.clsaa.ms.hermes.entity.po.WorkOrder;
import com.clsaa.ms.hermes.entity.po.WorkOrderComment;
import com.clsaa.ms.hermes.entity.vo.WorkOrderCommentV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    WorkOrder workOrder = this.workOrderDao.getById(workOrderId);
    BizAssert.found(workOrder != null, BizCodes.INVALID_PARAM.getCode(), "工单不存在");
    String customerId = workOrder.getCustomerId();
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
