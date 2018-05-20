package com.clsaa.ms.hermes.constant.state;

/**
 * 可转为 已结单状态
 *
 * @author 任贵杰
 * @version v1
 * @summary 待评价
 * @since 2018/5/1
 */
public class WaitEvaluatedState extends AbstractState {
  @Override
  boolean doValidateState(OrderState stateTo) {
    return stateTo.equals(OrderState.已结单);
  }
}
