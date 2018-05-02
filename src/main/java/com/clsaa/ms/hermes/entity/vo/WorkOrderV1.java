package com.clsaa.ms.hermes.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 工单视图层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class WorkOrderV1 {
  /**
   * 主键id
   */
  private String id;
  /**
   * 客户id,t_customer.id
   */
  private String customerId;
  /**
   * 责任人,某客服或主管id
   */
  private String dutyStaffId;
  /**
   * 编号,如20180425XXXXXX
   */
  private String code;
  /**
   * 标题
   */
  private String title;
  /**
   * 类型,1为安装及环境,2为功能使用,3为账号类,4为计费和财务,5为备案及流程,6为其他
   */
  private Integer type;
  /**
   * 描述
   */
  private String description;
  /**
   * 接收消息通知选项,0为从不接收,1为任何时间都接收,2为9-18点接收
   */
  private Integer receiveMsgType;
  /**
   * 接受电话回访选项,0为从不接收,1为任何时间都接收,2为工作日接收
   */
  private Integer callbackType;
  /**
   * 每天能接受回访的开始时间,如0930
   */
  private Integer callbackTimeBegin;
  /**
   * 每天能接受回访的结束时间,如1830
   */
  private Integer callbackTimeEnd;
  /**
   * 实际完成时间
   */
  private Timestamp finishTime;
  /**
   * 创建时间
   */
  private Timestamp ctime;
  /**
   * 状态,1为未处理,2为处理中,3为待补充,4为待确认结单,5为待评价,6为已撤销,7为已结单
   */
  private Integer status;
  /**
   * 工单对话列表
   */
  private List<WorkOrderCommentV1> workOrderCommentV1List;
}
