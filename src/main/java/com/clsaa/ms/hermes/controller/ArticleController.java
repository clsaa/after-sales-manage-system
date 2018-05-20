package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.WorkOrderTypeEnum;
import com.clsaa.ms.hermes.entity.dto.ArticleDtoV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author 任贵杰
 * @version v1
 * @summary 文章接口实现
 * @since 2018/5/20
 */
@RestController
@CrossOrigin
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

}
