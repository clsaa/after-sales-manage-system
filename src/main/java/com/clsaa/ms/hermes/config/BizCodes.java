package com.clsaa.ms.hermes.config;

import com.clsaa.ms.hermes.result.BizCode;

/**
 * @author 任贵杰
 */
public interface BizCodes {
  /**
   * 非法请求
   */
  BizCode INVALID_PARAM = new BizCode(10000, "请求参数非法");
  /**
   * 数据库插入失败
   */
  BizCode ERROR_INSERT = new BizCode(1010, "新增失败");
  /**
   * 数据库更新失败
   */
  BizCode ERROR_UPDATE = new BizCode(1011, "更新失败");
  /**
   * 错误的目标工单状态
   */
  BizCode INVALID_WORK_ORDER_STATUS = new BizCode(1020, "当前工单状态不可转为目标状态");
}
