package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.ArticleDao;
import com.clsaa.ms.hermes.entity.po.Article;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章服务类
 * @since 2018/5/20
 */
@Service
public class ArticleService {
  @Autowired
  private ArticleDao articleDao;

  public String add(String loginUserId, Integer type, String title, String content) {
    Article article = new Article(UUIDUtil.getUUID(), type, title, content, loginUserId, loginUserId);
    int count = 0;
    try {
      count = this.articleDao.add(article);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return article.getId();
  }

  public Boolean del(String articleId) {
    int count = 0;
    try {
      count = this.articleDao.delById(articleId);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_DELETE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_DELETE);
    return true;
  }
}
