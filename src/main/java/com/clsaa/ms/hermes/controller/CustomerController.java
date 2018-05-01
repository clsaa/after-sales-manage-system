package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.CustomerTypeEnum;
import com.clsaa.ms.hermes.constant.GenderEnum;
import com.clsaa.ms.hermes.entity.dto.CustomerDtoV1;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.service.CustomerService;
import com.clsaa.ms.hermes.util.EmailValidator;
import com.clsaa.ms.hermes.util.MobileValidator;
import com.clsaa.ms.hermes.util.QQValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.sql.Date;

/**
 * @author 任贵杰
 * @version v1
 * @summary 顾客相关接口
 * @since 2018/4/29
 */
@CrossOrigin
@RestController
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  private void doValidation(String loginUserId, Integer type, String name, Integer age, Long birthday, Integer gender, String mobile, String email, String wechat, String qq, String note) {
    BizAssert.validParam(loginUserId != null, BizCodes.INVALID_PARAM.getCode(), "顾客类型错误");
    BizAssert.validParam(CustomerTypeEnum.getByCode(type) != null, BizCodes.INVALID_PARAM.getCode(), "顾客类型错误");
    if (StringUtils.hasText(name)) {
      BizAssert.validParam(name.length() < 50, BizCodes.INVALID_PARAM.getCode(), "姓名不能超过50个字符");
    }
    if (age != null) {
      BizAssert.validParam(age >= 0 && age <= 150, BizCodes.INVALID_PARAM.getCode(), "年龄不合法");
    }
    if (birthday != null) {
      try {
        new Date(birthday);
      } catch (Exception e) {
        BizAssert.justInvalidParam(BizCodes.INVALID_PARAM.getCode(), "生日不合法");
      }
    }
    if (gender != null) {
      BizAssert.validParam(GenderEnum.getByCode(gender) != null, BizCodes.INVALID_PARAM.getCode(), "性别类型错误");
    }
    BizAssert.validParam(MobileValidator.isChinaMobile(mobile), BizCodes.INVALID_PARAM.getCode(), "手机号格式错误");
    if (StringUtils.hasText(email)) {
      BizAssert.validParam(EmailValidator.isEmail(email), BizCodes.INVALID_PARAM.getCode(), "邮箱格式错误");
    }
    if (StringUtils.hasText(qq)) {
      BizAssert.validParam(QQValidator.isQQ(qq), BizCodes.INVALID_PARAM.getCode(), "QQ格式错误");
    }
    if (StringUtils.hasText(wechat)) {
      BizAssert.validParam(wechat.length() > 6 && wechat.length() < 20, BizCodes.INVALID_PARAM.getCode(), "微信号格式错误");
    }
    if (StringUtils.hasText(note)) {
      BizAssert.validParam(note.length() < 180, BizCodes.INVALID_PARAM.getCode(), "备注不能超过180个字符");
    }
  }

  /**
   * 根据id查询顾客信息,若所查询id不存在则返回null
   *
   * @param id 顾客id
   * @return {@link Mono<CustomerV1>}对象
   * @summary 根据id查询顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @GetMapping("/v1/customer/{id}")
  public Mono<CustomerV1> getByIdV1(@PathVariable(value = "id") String id) {
    return this.customerService.getCustomerV1ById(id);
  }

  /**
   * 根据手机号查询顾客信息,若所查询id不存在则返回null
   *
   * @param mobile 顾客手机号
   * @return {@link Mono<CustomerV1>}对象
   * @summary 根据手机号查询顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @GetMapping("/v1/customer/ByMobile")
  public Mono<CustomerV1> getByMobileV1(@RequestParam(value = "mobile") String mobile) {
    return this.customerService.getCustomerV1ByMobile(mobile);
  }

  /**
   * 添加顾客信息,若添加成功返回插入顾客的id
   *
   * @param loginUserId 登录用户id
   * @param customerDtoV1 {@link CustomerDtoV1}
   * @return 已添加的顾客id
   * @summary 添加顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @PostMapping("/v1/customer")
  public Mono<String> addV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                            @RequestBody CustomerDtoV1 customerDtoV1) {
    Integer type = customerDtoV1.getType();
    String name = customerDtoV1.getName();
    Integer age = customerDtoV1.getAge();
    Long birthday = customerDtoV1.getBirthday();
    Integer gender = customerDtoV1.getGender();
    String mobile = customerDtoV1.getMobile();
    String email = customerDtoV1.getEmail();
    String wechat = customerDtoV1.getWechat();
    String qq = customerDtoV1.getQq();
    String note = customerDtoV1.getNote();
    this.doValidation(loginUserId, type, name, age, birthday, gender, mobile, email, wechat, qq, note);
    return this.customerService.add(loginUserId, type, name, age, birthday, gender, mobile, email, wechat, qq, note);
  }

  /**
   * 根据id删除顾客信息
   *
   * @param id 顾客id
   * @return {@link Mono<CustomerV1>}对象
   * @summary 根据id删除顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @DeleteMapping("/v1/customer/{id}")
  public Mono<Void> delByIdV1(@PathVariable(value = "id") String id) {
    return this.customerService.delById(id);
  }

  /**
   * 根据id列表批量删除顾客信息
   *
   * @param ids 顾客id列表,半角逗号分割:1,2,3,5
   * @return {@link Mono<CustomerV1>}对象
   * @summary 批量删除顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @DeleteMapping("/v1/customer/batch")
  public Mono<Void> delBatchByIdsV1(@RequestParam(value = "ids") String[] ids) {
    return this.customerService.delBatchByIds(ids);
  }

  /**
   * 修改顾客信息,若添加成功则静默
   *
   * @param loginUserId 登录用户id
   * @param id          顾客id
   * @param customerDtoV1 {@link CustomerDtoV1}
   * @return {@link Mono<Void>}
   * @summary 修改顾客信息
   * @version v1
   * @author 任贵杰
   * @since 2018/4/29
   */
  @PutMapping("/v1/customer/{id}")
  public Mono<Void> updateV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                             @PathVariable(value = "id") String id,
                             @RequestBody CustomerDtoV1 customerDtoV1) {
    Integer type = customerDtoV1.getType();
    String name = customerDtoV1.getName();
    Integer age = customerDtoV1.getAge();
    Long birthday = customerDtoV1.getBirthday();
    Integer gender = customerDtoV1.getGender();
    String mobile = customerDtoV1.getMobile();
    String email = customerDtoV1.getEmail();
    String wechat = customerDtoV1.getWechat();
    String qq = customerDtoV1.getQq();
    String note = customerDtoV1.getNote();
    this.doValidation(loginUserId, type, name, age, birthday, gender, mobile, email, wechat, qq, note);
    return this.customerService.update(id, loginUserId, type, name, age, birthday, gender, mobile, email, wechat, qq, note);
  }

  /**
   * 分页查询客户信息,按更新时间降序排序
   *
   * @param loginUserId 登录用户id
   * @param type        顾客类型
   * @param gender      顾客性别
   * @param keyword     关键字,精确匹配顾客姓名与手机号
   * @param pageNo      分页页号,默认为1
   * @param pageSize    分页大小,默认为20
   * @return {@link Mono<Pagination>}
   * @summary 分页查询客户信息
   * @author 任贵杰
   * @since 2018/4/29
   */
  @GetMapping(value = "/v1/customer/pagination")
  public Mono<Pagination<CustomerV1>> getPaginationV1(@RequestHeader(value = "Login-User-Id", defaultValue = "") String loginUserId,
                                                      @RequestParam(value = "type", required = false) Integer type,
                                                      @RequestParam(value = "gender", required = false) Integer gender,
                                                      @RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
    return this.customerService.getPagination(loginUserId, type, gender, keyword, pageNo, pageSize);
  }
}
