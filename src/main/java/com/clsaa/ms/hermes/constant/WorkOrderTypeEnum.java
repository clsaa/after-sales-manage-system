package com.clsaa.ms.hermes.constant;

/**
 * @author 任贵杰
 * @version v1
 * @summary 工单类型常量
 * @since 2018-05-01
 */
public enum WorkOrderTypeEnum {
  /**
   * 工单名:安装及环境,编码:1
   */
  安装及环境(1),
  /**
   * 工单名:功能使用,编码:2
   */
  功能使用(2),
  /**
   * 工单名:账号类,编码:3
   */
  账号类(3),
  /**
   * 工单名:计费和财务,编码:4
   */
  计费和财务(4),
  /**
   * 工单名:备案及流程,编码:5
   */
  备案及流程(5),
  /**
   * 工单名:其他,编码:6
   */
  其他(6);
  private int code;

  WorkOrderTypeEnum(int code) {
    this.code = code;
  }

  public static WorkOrderTypeEnum getByCode(int code) {
    for (WorkOrderTypeEnum type : WorkOrderTypeEnum.values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }
}
