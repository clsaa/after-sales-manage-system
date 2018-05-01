package com.clsaa.ms.hermes.service;

import com.clsaa.ms.hermes.config.BizCodes;
import com.clsaa.ms.hermes.constant.state.StateContext;
import com.clsaa.ms.hermes.dao.WordOrderDao;
import com.clsaa.ms.hermes.entity.dto.CustomerDtoV1;
import com.clsaa.ms.hermes.entity.po.WorkOrder;
import com.clsaa.ms.hermes.entity.vo.CustomerV1;
import com.clsaa.ms.hermes.entity.vo.WorkOrderCommentV1;
import com.clsaa.ms.hermes.entity.vo.WorkOrderV1;
import com.clsaa.ms.hermes.result.BizAssert;
import com.clsaa.ms.hermes.result.Pagination;
import com.clsaa.ms.hermes.util.OrderCodeUtil;
import com.clsaa.ms.hermes.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 任贵杰
 * @summary 工单服务
 * @since 2018/4/29
 */
@Service
public class WorkOrderService {
  @Autowired
  private WorkOrderCommentService workOrderCommentService;
  @Autowired
  private CustomerService customerService;
  @Autowired
  private WordOrderDao wordOrderDao;

  private static WorkOrderV1 valueOf(WorkOrder workOrder) {
    if (workOrder == null) {
      return null;
    } else {
      WorkOrderV1 workOrderV1 = new WorkOrderV1();
      BeanUtils.copyProperties(workOrder, workOrderV1);
      return workOrderV1;
    }
  }

  /**
   * 根据id查询工单详情(包含工单对话)
   *
   * @param id 工单id
   * @return {@link WorkOrderV1}
   */
  public WorkOrderV1 getWorkOrderV1ById(String id) {
    WorkOrder workOrder = this.wordOrderDao.getById(id);
    BizAssert.found(workOrder != null, BizCodes.INVALID_PARAM.getCode(), "工单不存在");
    List<WorkOrderCommentV1> workOrderCommentV1List = this.workOrderCommentService.getWorkOrderCommentV1ByWorkOrderId(workOrder.getId());
    WorkOrderV1 workOrderV1 = valueOf(workOrder);
    workOrderV1.setWorkOrderCommentV1List(workOrderCommentV1List);
    return workOrderV1;
  }

  /**
   * 创建工单,若客户手机号不存在则会在创建工单时创建一个客户(与创建工单在同一个事务中).
   *
   * @param name        客户姓名
   * @param mobile      客户手机号
   * @param type        工单类型
   * @param title       工单标题
   * @param description 工单描述
   * @return 创建的工单id
   */
  @Transactional(rollbackFor = Exception.class)
  public String add(String name, String mobile, Integer type, String title, String description) {
    CustomerV1 customerV1 = this.customerService.getCustomerV1ByMobile(mobile);
    String customerId;
    if (customerV1 == null) {
      //通过customerDTO获取默认值
      CustomerDtoV1 customerDtoV1 = new CustomerDtoV1();
      Integer customerType = customerDtoV1.getType();
      Integer age = customerDtoV1.getAge();
      Long birthday = customerDtoV1.getBirthday();
      Integer gender = customerDtoV1.getGender();
      String email = customerDtoV1.getEmail();
      String wechat = customerDtoV1.getWechat();
      String qq = customerDtoV1.getQq();
      String note = customerDtoV1.getNote();
      customerId = this.customerService.add("", customerType, name, age, birthday, gender, mobile, email, wechat, qq, note);
    } else {
      customerId = customerV1.getId();
    }
    WorkOrder workOrder = new WorkOrder(UUIDUtil.getUUID(), customerId, OrderCodeUtil.createOrderCode(),
      title, type, description, customerId, customerId, WorkOrder.STATUS_PENDING);
    int count = 0;
    try {
      count = this.wordOrderDao.add(workOrder);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_INSERT);
    return workOrder.getId();
  }


  /**
   * 获取某个客服可见(没人对此工单负责或本客服负责的工单)
   *
   * @param loginUserId 登录用户id
   * @param type        工单类型
   * @param status      工单状态
   * @param beginTime   开始时间
   * @param endTime     结束时间
   * @param pageNo      页号
   * @param pageSize    页大小
   * @return {@link Pagination<WorkOrderV1>}
   */
  public Pagination<WorkOrderV1> getPagination(String loginUserId, Integer type, Integer status, Timestamp beginTime, Timestamp endTime, Integer pageNo, Integer pageSize) {

    int count = this.wordOrderDao.getPaginationCount(loginUserId, type, status, beginTime, endTime);

    Pagination<WorkOrderV1> pagination = new Pagination<>();
    pagination.setPageNo(pageNo);
    pagination.setPageSize(pageSize);
    pagination.setTotalCount(count);

    if (count == 0) {
      pagination.setPageList(Collections.emptyList());
      return pagination;
    }

    List<WorkOrderV1> workOrderV1List = this.wordOrderDao.getPaginationList(loginUserId, type, status,
      beginTime, endTime, pagination.getRowOffset(), pagination.getPageSize())
      .stream().map(WorkOrderService::valueOf).collect(Collectors.toList());

    pagination.setPageList(workOrderV1List);
    return pagination;
  }

  public Void updateStatus(String loginUserId, String id, Integer statusFrom, Integer statusTo) {
    //状态模式校验目标状态是否合法
    StateContext context = new StateContext();
    context.validateState(statusFrom, statusTo);
    //校验数据库查看是否已经被修改
    WorkOrder workOrder = this.wordOrderDao.getById(id);
    BizAssert.pass(workOrder.getStatus().equals(statusFrom), BizCodes.INVALID_PARAM.getCode(), "订单状态已被修改");
    workOrder.setStatus(statusTo);
    workOrder.setMuser(loginUserId);
    //更新数据
    int count = 0;
    try {
      count = this.wordOrderDao.update(workOrder);
    } catch (Exception e) {
      BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    }
    BizAssert.pass(count == 1, BizCodes.ERROR_UPDATE);
    return null;
  }
}
