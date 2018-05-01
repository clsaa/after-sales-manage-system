package com.clsaa.ms.hermes.dao;


import com.clsaa.ms.hermes.entity.po.WorkOrderComment;

import java.util.List;

/**
 * @author 任贵杰
 * @version v1
 * @summary 工单对话DAO
 * @since 2018-04-29
 */
public interface WordOrderCommentDao {

  /**
   * 根据工单id查询全部对话列表
   *
   * @param workOrderId 根据工单id查询全部对话列表
   * @return {@link List<WorkOrderComment>}
   */
  List<WorkOrderComment> getByWorkOrderId(String workOrderId);

  /**
   * 添加工单对话
   *
   * @param workOrderComment {@link WorkOrderComment}
   * @return 添加记录数
   */
  int add(WorkOrderComment workOrderComment);
}
