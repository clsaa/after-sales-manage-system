package com.clsaa.ms.hermes.constant;

/**
 * @author 任贵杰
 * @version v1
 * @summary 顾客类型常量
 * @since 2018/4/29
 */
public enum CustomerTypeEnum {
  /**
   * 性别名:女,编码:1
   */
  普通(1),
  /**
   * 性别名:女,编码:1
   */
  VIP(2),
  /**
   * 性别名:男,编码:2
   */
  SVIP(3);
  private int code;

  CustomerTypeEnum(int code) {
    this.code = code;
  }

  public static CustomerTypeEnum getByCode(int code) {
    for (CustomerTypeEnum type : CustomerTypeEnum.values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }
}
