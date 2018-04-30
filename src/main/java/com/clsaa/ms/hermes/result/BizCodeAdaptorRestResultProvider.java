package com.clsaa.ms.hermes.result;

import org.springframework.util.ClassUtils;


/**
 * 适配SPI VIEW
 */
public class BizCodeAdaptorRestResultProvider implements RestResultProvider {
  /**
   * SPI VIEW是否存在
   */
  private final boolean bizcodeExist =
      ClassUtils.isPresent("com.clsaa.ms.hermes.result.BizCode",
          this.getClass().getClassLoader());

  @Override
  public boolean support(Object source) {
    return bizcodeExist && source instanceof BizCode;
  }

  @Override
  public RestResult produce(Object source) {
    BizCode bizCode = BizCode.class.cast(source);
    return new StandardRestResult(bizCode.getCode(), bizCode.getMessage());
  }

}
