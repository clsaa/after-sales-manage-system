package com.clsaa.ms.hermes.result;


/**
 * 默认实现
 */
public class DefaultRestResultProvider implements RestResultProvider {

  @Override
  public boolean support(Object source) {
    return source instanceof RestResult;
  }

  @Override
  public RestResult produce(Object source) {
    return RestResult.class.cast(source);
  }

}
