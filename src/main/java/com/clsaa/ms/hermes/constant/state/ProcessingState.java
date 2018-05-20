package com.clsaa.ms.hermes.constant.state;

/**
 * 可转为 待补充状态/待确认结单状态/待评价状态/已撤销状态
 *
 * @author 任贵杰
 * @version v1
 * @summary 处理中
 * @since 2018/5/1
 */
public class ProcessingState extends AbstractState {
  @Override
  boolean doValidateState(OrderState stateTo) {
    return stateTo.equals(OrderState.待补充)
      || stateTo.equals(OrderState.待确认结单)
      || stateTo.equals(OrderState.待评价)
      || stateTo.equals(OrderState.已撤销);
  }
}
