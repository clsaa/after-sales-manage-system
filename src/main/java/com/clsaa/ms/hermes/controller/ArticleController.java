package com.clsaa.ms.hermes.controller;

import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.WorkOrderTypeEnum;
import com.clsaa.ms.hermes.entity.ArticleV1;
import com.clsaa.ms.hermes.entity.dto.ArticleDtoV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章接口实现
 * @since 2018/5/20
 */
@CrossOrigin
@RestController
public class ArticleController {
  @Autowired
  private ArticleService articleService;

  /**
   * 参数校验
   */
  private void doValidation(Integer type, String title, String content) {
    BizAssert.validParam(WorkOrderTypeEnum.getByCode(type) != null,
      BizCodes.INVALID_PARAM.getCode(), "文章类型错误");
    BizAssert.validParam(StringUtils.hasText(title) && title.length() <= 60,
      BizCodes.INVALID_PARAM.getCode(), "文章标题过长,不应超过60个字符");
    BizAssert.validParam(StringUtils.hasText(content) && content.length() <= 5000,
      BizCodes.INVALID_PARAM.getCode(), "文章过长,不应超过5000个字符");
  }

  @PostMapping(value = "/v1/article")
  public Mono<String> addV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                            @RequestBody ArticleDtoV1 articleDtoV1) {
    this.doValidation(articleDtoV1.getType(), articleDtoV1.getTitle(), articleDtoV1.getContent());
    return Mono.create(monoSink ->
      monoSink.success(this.articleService.add(loginUserId, articleDtoV1.getType(), articleDtoV1.getTitle(), articleDtoV1.getContent())));
  }

  @DeleteMapping(value = "/v1/article/{articleId}")
  public Mono<Boolean> delV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                             @PathVariable(value = "articleId") String articleId) {
    return Mono.create(monoSink ->
      monoSink.success(this.articleService.del(articleId)));
  }

  @PutMapping(value = "/v1/article/{articleId}")
  public Mono<Boolean> updateV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                @PathVariable(value = "articleId") String articleId,
                                @RequestBody ArticleDtoV1 articleDtoV1) {
    this.doValidation(articleDtoV1.getType(), articleDtoV1.getTitle(), articleDtoV1.getContent());
    return Mono.create(monoSink ->
      monoSink.success(this.articleService.update(loginUserId, articleId, articleDtoV1.getType(), articleDtoV1.getTitle(), articleDtoV1.getContent())));
  }

  @GetMapping(value = "/v1/article/{articleId}")
  public Mono<ArticleV1> updateV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                @PathVariable(value = "articleId") String articleId) {
    return Mono.create(monoSink ->
      monoSink.success(this.articleService.getArticleV1ById(loginUserId,articleId)));
  }

  @GetMapping("v1/article/search")
  public Mono<Pagination<ArticleV1>> search(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                            @RequestParam(value = "type", required = false) Integer type,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) throws IOException, OpenSearchClientException, OpenSearchException {
    return Mono.create(monoSink -> monoSink.success(this.articleService.search(loginUserId, keyword, type, pageNo, pageSize)));
  }
}
