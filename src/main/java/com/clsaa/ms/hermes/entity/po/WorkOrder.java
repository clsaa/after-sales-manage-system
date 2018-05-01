package com.clsaa.ms.hermes.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 工单持久层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class WorkOrder {
  public static final int STATUS_PENDING = 1;
  public static final int STATUS_PROCESSING = 2;
  public static final int STATUS_WAIT_SUPPLEMENTAL = 3;
  public static final int STATUS_WAIT_CONFIRMED = 4;
  public static final int STATUS_WAIT_EVALUATED = 5;
  public static final int STATUS_CANCELED = 6;
  public static final int STATUS_FINISHED = 7;
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
  private Integer callbackBeginTime;
  /**
   * 每天能接受回访的结束时间,如1830
   */
  private Integer callbackEndTime;
  /**
   * 结单时间
   */
  private Timestamp finishTime;
  /**
   * 创建时间
   */
  private Timestamp ctime;
  /**
   * 创建人
   */
  private String cuser;
  /**
   * 修改时间
   */
  private Timestamp mtime;
  /**
   * 修改人
   */
  private String muser;
  /**
   * 是否展示,0为不想用户展示,1为向用户展示
   */
  private Boolean show;
  /**
   * 状态,1为未处理,2为处理中,3为待补充,4为待确认结单,5为待评价,6为已撤销,7为已结单
   */
  private Integer status;

  public WorkOrder() {
  }

  public WorkOrder(String id, String customerId, String code, String title, Integer type, String description, String cuser, String muser, Integer status) {
    this.id = id;
    this.customerId = customerId;
    this.code = code;
    this.title = title;
    this.type = type;
    this.description = description;
    this.cuser = cuser;
    this.muser = muser;
    this.status = status;
  }
}
