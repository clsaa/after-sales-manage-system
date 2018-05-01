package com.clsaa.ms.hermes.constant.state;

/**
 * 可转为 处理中状态/待评价状态/已撤销状态
 *
 * @author 任贵杰
 * @version v1
 * @summary 待确认结单状态
 * @since 2018/5/1
 */
public class WaitConfirmedState extends AbstractState {
  @Override
  boolean doValidateState(OrderState stateTo) {
    return stateTo.equals(OrderState.处理中)
      || stateTo.equals(OrderState.待评价)
      || stateTo.equals(OrderState.已撤销);
  }
}
