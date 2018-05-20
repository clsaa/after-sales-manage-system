package com.clsaa.ms.hermes.util;

import java.util.regex.Pattern;

/**
 * @author 任贵杰
 * @version v1
 * @summary 手机号校验器
 * @since 2018/4/29
 */
public class MobileValidator {

  private MobileValidator() {
    throw new UnsupportedOperationException();
  }

  /**
   * 精确的中国大陆手机号校验正则表达式
   * <p>
   * <p>
   * 运营商号段如下：
   * 中国联通号码：130、131、132、145、155、156、185、186、176、175
   * 中国移动号码：134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188、178
   * 中国电信号码：133、153、180、181、189、177、173、149、170、1718、1719
   * 手机号前3位的数字包括：
   * 1 :1
   * 2 :3,4,5,7,8
   * 3 :0,1,2,3,4,5,6,7,8,9
   */
  private static final Pattern CHINA_MOBILE_PATTERN = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");

  public static boolean isChinaMobile(String mobile) {
    return CHINA_MOBILE_PATTERN.matcher(mobile).matches();
  }

}
