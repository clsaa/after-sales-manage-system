package com.clsaa.ms.hermes.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 顾客视图层对象
 * @since 2018/4/25
 */
@Getter
@Setter
public class CustomerV1 {
  /**
   * 主键id
   */
  private String id;
  /**
   * 分类,1为普通,2为VIP,3为SVIP
   */
  private Integer type;
  /**
   * 姓名
   */
  private String name;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 出生年月日
   */
  private Date birthday;
  /**
   * 性别,0为保密,1为女,2为男
   */
  private Integer gender;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 微信号
   */
  private String wechat;
  /**
   * QQ号
   */
  private String qq;
  /**
   * 备注
   */
  private String note;
  /**
   * 创建时间
   */
  private Timestamp ctime;
  /**
   * 修改时间
   */
  private Timestamp mtime;
}
