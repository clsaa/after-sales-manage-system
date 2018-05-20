package com.clsaa.ms.hermes.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章传输层对象
 * @since 2018/5/20
 */
@Getter
@Setter
public class ArticleDtoV1 {
  /**
   * 类型,1为安装及环境,2为功能使用,3为账号类,4为计费和财务,5为备案及流程,6为其他
   */
  private Integer type;
  /**
   * 标题
   */
  private String title;
  /**
   * 内容
   */
  private String content;
}
