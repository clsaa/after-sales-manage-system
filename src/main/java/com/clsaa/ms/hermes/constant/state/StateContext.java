package com.clsaa.ms.hermes.constant.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link OrderState}的环境
 * 业务真正操作的对象，根据传入的状态，找到对应的{@link AbstractState}实现
 *
 * @author 任贵杰
 */
public class StateContext {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 判断订单状态是否满足切换条件
   * 对业务暴露的方法，调用持有的state进行真正的操作
   *
   * @param stateFrom 订单原状态
   * @param stateTo   订单目标状态
   * @return 是否可以切换
   */
  public boolean validateState(int stateFrom, int stateTo) {
    AbstractState state = getStateClass(stateFrom);
    OrderState eStateTo = OrderState.getByCode(stateTo);
    return state.validateState(eStateTo);
  }

  /**
   * 根据订单状态实例化对应的{@link AbstractState}子类对象
   * 订单状态和State对象的映射关系在{@link OrderState}中
   *
   * @param stateFrom 订单原状态
   * @return ? extends {@link AbstractState}
   */
  private AbstractState getStateClass(int stateFrom) {
    OrderState eStateFrom = OrderState.getByCode(stateFrom);
    Class<? extends AbstractState> clz = eStateFrom.getMappingStateClass();
    AbstractState localState = null;
    try {
      localState = clz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error("{}初始化失败", clz.getName());
    }
    return localState;
  }
}
