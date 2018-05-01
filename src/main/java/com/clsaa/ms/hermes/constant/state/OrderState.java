package com.clsaa.ms.hermes.constant.state;

import com.clsaa.ms.hermes.result.exception.StandardBusinessException;
import lombok.Getter;

/**
 * 定义工单状态枚举
 *
 * @author 任贵杰
 */
@Getter
public enum OrderState {
  /**
   * @see PendingState
   */
  未处理(1, PendingState.class),
  /**
   * @see ProcessingState
   */
  处理中(2, ProcessingState.class),
  /**
   * @see WaitSupplementalState
   */
  待补充(3, WaitSupplementalState.class),
  /**
   * @see WaitConfirmedState
   */
  待确认结单(4, WaitConfirmedState.class),
  /**
   * @see WaitEvaluatedState
   */
  待评价(5, WaitEvaluatedState.class),
  /**
   * @see CanceledState
   */
  已撤销(6, CanceledState.class),
  /**
   * @see FinishedState
   */
  已结单(7, FinishedState.class);
  /**
   * 对应数据库的状态码
   */
  private int stateCode;
  /**
   * 对应状态模式的子类
   */
  private Class<? extends AbstractState> mappingStateClass;

  OrderState(int stateCode, Class<? extends AbstractState> mappingStateClass) {
    this.stateCode = stateCode;
    this.mappingStateClass = mappingStateClass;
  }

  public static OrderState getByCode(int stateCode) {
    for (OrderState orderState : values()) {
      if (orderState.stateCode == stateCode) {
        return orderState;
      }
    }
    throw new StandardBusinessException("非法的订单状态:" + stateCode);
  }
}
