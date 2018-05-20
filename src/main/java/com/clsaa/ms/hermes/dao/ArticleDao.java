package com.clsaa.ms.hermes.dao;

import com.clsaa.ms.hermes.entity.po.Article;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章DAO
 * @since 2018/5/20
 */
public interface ArticleDao {
  /**
   * 添加article
   *
   * @param article 文章
   * @return 影响记录数
   */
  int add(Article article);

  /**
   * 通过id删除文章
   *
   * @param id 文章id
   * @return 影响记录数
   */
  int delById(String id);
}
