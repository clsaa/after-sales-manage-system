package com.clsaa.ms.hermes.util;

import java.util.regex.Pattern;

/**
 * @author 任贵杰
 * @version v1
 * @summary QQ号校验器
 * @since 2018/4/29
 */
public class QQValidator {

  private QQValidator() {
    throw new UnsupportedOperationException();
  }

  private static final Pattern QQ_PATTERN = Pattern.compile("[1-9][0-9]{4,14}");

  public static boolean isQQ(String qq) {
    return QQ_PATTERN.matcher(qq).matches();
  }
}
