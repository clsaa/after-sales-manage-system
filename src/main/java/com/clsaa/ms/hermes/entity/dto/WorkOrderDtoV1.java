package com.clsaa.ms.hermes.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 工单传输层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class WorkOrderDtoV1 {
  /**
   * 客户姓名
   */
  private String name;
  /**
   * 客户手机号
   */
  private String mobile;
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
}
