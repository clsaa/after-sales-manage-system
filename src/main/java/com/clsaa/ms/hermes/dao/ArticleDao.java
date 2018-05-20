package com.clsaa.ms.hermes.dao;

import com.clsaa.ms.hermes.entity.po.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

  /**
   * 根据id查询文章
   *
   * @param id
   * @return {@link Article}
   */
  Article getById(String id);

  /**
   * 更新文章
   *
   * @param article 文章持久层对象
   * @return 影响记录数
   */
  int update(Article article);

  /**
   * 获取分页数据总量
   *
   * @param type 类型
   * @return 分页数据总量
   */
  int getPaginationCount(@Param("type") Integer type);

  /**
   * 获取分页数据
   *
   * @param type      类型
   * @param rowOffset 偏移
   * @param pageSize  页大小
   * @return {@link List<Article>}
   */
  List<Article> getPaginationList(@Param("type") Integer type,
                                  @Param("rowOffset") int rowOffset,
                                  @Param("pageSize") int pageSize);
}
