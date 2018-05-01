package com.clsaa.ms.hermes.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author RenGuiJie
 * @version v1
 * @summary 计划视图层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class PlanV1 {
  /**
   * 主键id
   */
  private String id;
  /**
   * 计划类型,1,为下次联系计划,2为回访计划,3为上级任务计划,4为合同到期计划,5为回款计划,6为收款计划,7为客户生日计划,8为预约计划,9为个人计划
   */
  private Integer type;
  /**
   * 是否重要,0为不重要,1为重要
   */
  private Boolean important;
  /**
   * 是否紧急,0为不紧急,1为紧急
   */
  private Boolean urgent;
  /**
   * 备注
   */
  private String note;
  /**
   * 计划开始时间
   */
  private Timestamp beginTime;
  /**
   * 计划完成时间
   */
  private Timestamp endTime;
  /**
   * 实际完成时间
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
   * 状态,0为已删除,1为待完成,2为已完成,4为已过期
   */
  private Integer status;
}

