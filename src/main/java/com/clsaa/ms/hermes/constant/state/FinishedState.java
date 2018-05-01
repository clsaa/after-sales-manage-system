package com.clsaa.ms.hermes.constant.state;

/**
 * 不可转为任何其他状态
 * @author 任贵杰
 * @version v1
 * @summary 已完成状态
 * @since 2018/5/1
 */
public class FinishedState extends AbstractState{
  @Override
  boolean doValidateState(OrderState stateTo) {
    return false;
  }
}
