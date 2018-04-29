package com.clsaa.ms.hermes.constant;

/**
 * @author 任贵杰
 * @version v1
 * @summary 性别枚举
 * @since 2018/4/29
 */
public enum GenderEnum {
  /**
   * 性别名:无,编码:0
   */
  保密(0),
  /**
   * 性别名:女,编码:1
   */
  女(1),
  /**
   * 性别名:男,编码:2
   */
  男(2);
  private int code;

  GenderEnum(int code) {
    this.code = code;
  }

  public static GenderEnum getByCode(int code) {
    for (GenderEnum gender : GenderEnum.values()) {
      if (gender.code == code) {
        return gender;
      }
    }
    return null;
  }
}
