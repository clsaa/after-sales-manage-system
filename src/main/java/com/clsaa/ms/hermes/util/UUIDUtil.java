package com.clsaa.ms.hermes.util;

import java.util.UUID;

public class UUIDUtil {

  /**
   * 自动生成32位的UUid，对应数据库的主键id进行插入用。
   */
  public static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
