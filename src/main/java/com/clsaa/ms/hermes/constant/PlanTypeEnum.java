package com.clsaa.ms.hermes.constant;

/**
 * @author 任贵杰
 * @version v1
 * @summary 计划类型常量
 * @since 2018/4/29
 */
public enum PlanTypeEnum {
  //   * 计划类型,1,为下次联系计划,2为回访计划,3为上级任务计划,4为合同到期计划,5为回款计划,6为收款计划,7为客户生日计划,8为预约计划,9为个人计划
  /**
   * 计划名:下次联系计划,编码:1
   */
  下次联系计划(1),
  /**
   * 计划名:下次联系计划,编码:2
   */
  回访计划(2),
  /**
   * 计划名:下次联系计划,编码:3
   */
  上级任务计划(3),
  /**
   * 计划名:下次联系计划,编码:4
   */
  合同到期计划(4),
  /**
   * 计划名:下次联系计划,编码:5
   */
  回款计划(5),
  /**
   * 计划名:下次联系计划,编码:6
   */
  收款计划(6),
  /**
   * 计划名:下次联系计划,编码:7
   */
  客户生日计划(7),
  /**
   * 计划名:下次联系计划,编码:8
   */
  预约计划(8),
  /**
   * 计划名:个人计划,编码:9
   */
  个人计划(9);
  private int code;

  PlanTypeEnum(int code) {
    this.code = code;
  }

  public static PlanTypeEnum getByCode(int code) {
    for (PlanTypeEnum type : PlanTypeEnum.values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }
}
