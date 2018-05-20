package com.clsaa.ms.hermes.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章持久层对象
 * @since 2018/5/20
 */
@Getter
@Setter
public class ArticleV1 {
  /**
   * 主键
   */
  private String id;
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
  /**
   * 创建时间
   */
  private Timestamp ctime;
  /**
   * 修改时间
   */
  private Timestamp mtime;
}
