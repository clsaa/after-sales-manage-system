package com.clsaa.ms.hermes.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.clsaa.ms.hermes.config.AliOpenSearch;
import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.dao.ArticleDao;
import com.clsaa.ms.hermes.entity.ArticleV1;
import com.clsaa.ms.hermes.entity.po.Article;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
  @Autowired
  private OpenSearchClient openSearchClient;
  @Autowired
  private AliOpenSearch aliOpenSearch;

  private static ArticleV1 valueOf(Article article) {
    if (article == null) {
      return null;
    } else {
      ArticleV1 articleV1 = new ArticleV1();
      BeanUtils.copyProperties(article, articleV1);
      return articleV1;
    }
  }

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

  public boolean update(String loginUserId, String articleId, Integer type, String title, String content) {
    Article article = this.articleDao.getById(articleId);
    BizAssert.found(article != null, BizCodes.INVALID_PARAM.getCode(), "无法找到对应文章");
    article.setMuser(loginUserId);
    article.setMtime(new Timestamp(System.currentTimeMillis()));
    article.setType(type);
    article.setTitle(title);
    article.setContent(content);
    int count = 0;
    try {
      count = this.articleDao.update(article);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return true;
  }

  public Pagination<ArticleV1> search(String loginUserId, String keyword, Integer type, Integer pageNo, Integer pageSize) {
    //创建分页对象
    Pagination<ArticleV1> pagination = new Pagination<>();
    pagination.setPageNo(pageNo);
    pagination.setPageSize(pageSize);
    //如果有关键词搜索走搜索引擎
    if (StringUtils.hasText(keyword)) {
      //创建SearcherClient对象，并以OpenSearchClient对象作为构造参数
      SearcherClient searcherClient = new SearcherClient(openSearchClient);
      //定义Config对象，用于设定config子句参数，指定应用名，分页，数据返回格式等等
      Config config = new Config(Lists.newArrayList(aliOpenSearch.getAppName()));
      config.setStart(pagination.getRowOffset());
      config.setHits(pagination.getPageSize());
      //设置返回格式为full json格式
      config.setSearchFormat(SearchFormat.JSON);
      //创建参数对象
      SearchParams searchParams = new SearchParams(config);
      //指定搜索的关键词，这里要指定在哪个索引上搜索，如果不指定的话默认在使用“default”索引（索引字段名称是您在您的数据结构中的“索引字段列表”中对应字段。），若需多个索引组合查询，需要在setQuery处合并，否则若设置多个setQuery子句，则后面的子句会替换前面子句
      searchParams.setQuery("content:'" + keyword + "'" + " OR " + "title:'" + keyword + "'");
      if (type != null) {
        //设置查询过滤条件
        searchParams.setFilter("type=" + type);
      }
      //创建sort对象，并设置二维排序
      Sort sort = new Sort();
      //以RANK相关性算分升序
      sort.addToSortFields(new SortField("RANK", Order.DECREASE));
      //执行查询语句返回数据对象
      SearchResult searchResult = null;
      try {
        searchResult = searcherClient.execute(searchParams);
      } catch (Exception e) {
        e.printStackTrace();
        BizAssert.justFailed("查询失败");
      }
      //以字符串返回查询数据
      String resultStr = searchResult.getResult();
      JSONObject result = (JSONObject) JSON.parseObject(resultStr).get("result");
      int total = (int) result.get("total");
      pagination.setTotalCount(total);
      List<ArticleV1> paginationList = ((JSONArray) result.get("items")).toJavaList(ArticleV1.class);
      pagination.setPageList(paginationList);
      return pagination;
    } else {
      int count = this.articleDao.getPaginationCount(type);
      pagination.setTotalCount(count);
      if (count == 0) {
        pagination.setPageList(Collections.emptyList());
        return pagination;
      }
      List<ArticleV1> paginationList = this.articleDao
        .getPaginationList(type, pagination.getRowOffset(), pagination.getPageSize())
        .stream()
        .map(ArticleService::valueOf)
        .collect(Collectors.toList());
      pagination.setPageList(paginationList);
      return pagination;
    }
  }

  public ArticleV1 getArticleV1ById(String loginUserId, String articleId) {
    return valueOf(this.articleDao.getById(articleId));
  }
}
