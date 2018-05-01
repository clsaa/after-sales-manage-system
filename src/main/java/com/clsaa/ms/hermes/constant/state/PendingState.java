package com.clsaa.ms.hermes.constant.state;

/**
 * 可转为 已撤销状态/待补充状态
 *
 * @author 任贵杰
 * @version v1
 * @summary 未处理状态
 * @since 2018/5/1
 */
public class PendingState extends AbstractState {

  @Override
  boolean doValidateState(OrderState stateTo) {
    return stateTo.equals(OrderState.已撤销)
      || stateTo.equals(OrderState.待补充);
  }
}
