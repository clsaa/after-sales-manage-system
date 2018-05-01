package com.clsaa.ms.hermes.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 工单对话视图层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class WorkOrderCommentV1 {
  /**
   * 主键id
   */
  private String id;
  /**
   * 工单id
   */
  private String workOrderId;
  /**
   * 顾客id
   */
  private String customerId;
  /**
   * 客服id
   */
  private String staffId;
  /**
   * 内容
   */
  private String content;
  /**
   * 创建时间
   */
  private Timestamp ctime;
}
