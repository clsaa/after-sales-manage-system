package com.clsaa.ms.hermes.util;

import java.util.regex.Pattern;

/**
 * @author 任贵杰
 * @version v1
 * @summary 邮箱校验器
 * @since 2018/4/29
 */
public class EmailValidator {

  private EmailValidator() {
    throw new UnsupportedOperationException();
  }

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

  public static boolean isEmail(String email) {
    return EMAIL_PATTERN.matcher(email).matches();
  }
}
