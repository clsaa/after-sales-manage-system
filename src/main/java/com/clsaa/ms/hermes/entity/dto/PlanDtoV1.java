package com.clsaa.ms.hermes.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 任贵杰
 * @version v1
 * @summary 计划传输层对象
 * @since 2018/4/29
 */
@Getter
@Setter
public class PlanDtoV1 {
  /**
   * 计划类型,1为下次联系计划,2为回访计划,3为上级任务计划,4为合同到期计划,5为回款计划,6为收款计划,7为客户生日计划,8为预约计划,9为个人计划
   */
  private Integer type = 9;
  /**
   * 是否重要,true为重要,false为不重要
   */
  private Boolean important = false;
  /**
   * 是否紧急,true为紧急,false为不紧急
   */
  private Boolean urgent = false;
  /**
   * 备注
   */
  private String note = "";
  /**
   * 计划开始时间
   */
  private Long beginTime = 0L;
  /**
   * 计划完成时间
   */
  private Long endTime = 0L;
}

