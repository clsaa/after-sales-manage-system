package com.clsaa.ms.hermes.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 顾客传输层对象
 * @since 2018/4/25
 */
@Getter
@Setter
public class CustomerDtoV1 {
  /**
   * 分类,1为普通,2为VIP,3为SVIP
   */
  private Integer type = 1;
  /**
   * 姓名
   */
  private String name = "";
  /**
   * 年龄
   */
  private Integer age = 0;
  /**
   * 出生年月日
   */
  private Long birthday = 0L;
  /**
   * 性别,0为保密,1为女,2为男
   */
  private Integer gender = 0;
  /**
   * 手机号
   */
  private String mobile = "";
  /**
   * 邮箱
   */
  private String email = "";
  /**
   * 微信号
   */
  private String wechat = "";
  /**
   * QQ号
   */
  private String qq = "";
  /**
   * 备注
   */
  private String note = "";
}
