package com.clsaa.ms.hermes.constant.state;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.result.BizAssert;

/**
 * <p>
 * 状态模式
 * 工单状态的抽象父类，定义了状态的行为，用于修改工单订单状态是否合法
 * </p>
 *
 * @author 任贵杰
 * @version v1
 * @summary 抽象状态
 * @since 2018/5/1
 */
public abstract class AbstractState {

  /**
   * 校验的抽象实现，具体实现在子类中
   *
   * @param stateTo 目标状态{@link OrderState}
   * @return 验证通过
   */
  abstract boolean doValidateState(OrderState stateTo);

  /**
   * 校验工单的当前状态能否更新为目标状态
   *
   * @param stateTo 目标状态{@link OrderState}
   * @return 验证通过
   */
  boolean validateState(OrderState stateTo) {
    BizAssert.pass(doValidateState(stateTo), BizCodes.INVALID_WORK_ORDER_STATUS);
    return true;
  }
}
