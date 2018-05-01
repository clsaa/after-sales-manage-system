package com.clsaa.ms.hermes.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 工单对话传输层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class WorkOrderCommentDtoV1 {
  public static final int SOURCE_CUSTOMER = 1;
  public static final int SOURCE_STAFF = 2;
  /**
   * 内容
   */
  private String content;
  /**
   * 来源,1为用户,2为客服
   */
  private Integer source;
}
