package com.clsaa.ms.hermes.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 顾客持久层对象
 * @since 2018/4/25
 */
@Getter
@Setter
public class Customer {
  public static final int STATUS_DEL = 0;
  public static final int STATUS_OK = 1;

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
   * 状态,0为已删除,1为有效
   */
  private Integer status;

  public Customer() {
  }

  public Customer(String id, Integer type, String name, Integer age, Date birthday, Integer gender, String mobile, String email, String wechat, String qq, String note, String cuser, String muser, Integer status) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.age = age;
    this.birthday = birthday;
    this.gender = gender;
    this.mobile = mobile;
    this.email = email;
    this.wechat = wechat;
    this.qq = qq;
    this.note = note;
    this.cuser = cuser;
    this.muser = muser;
    this.status = status;
  }
}
