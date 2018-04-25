package com.clsaa.ms.hermes.config;

import com.clsaa.ms.hermes.result.BizCode;

public interface BizCodes {
  /**
   * 非法请求
   */
  BizCode INVALID_REQUEST = new BizCode(1001, "请求非法");

}
