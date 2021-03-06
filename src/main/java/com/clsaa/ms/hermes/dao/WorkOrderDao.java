package com.clsaa.ms.hermes.dao;


import com.clsaa.ms.hermes.entity.po.WorkOrder;
import com.clsaa.ms.hermes.entity.vo.WorkOrderV1;
import com.clsaa.ms.hermes.result.Pagination;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 任贵杰
 * @version v1
 * @summary 工单DAO
 * @since 2018-04-29
 */
public interface WorkOrderDao {

  /**
   * 根据id查询工单信息
   *
   * @param id 工单id
   * @return {@link WorkOrder}
   */
  WorkOrder getById(String id);

  /**
   * 添加工单记录
   *
   * @param workOrder {@link WorkOrder}
   * @return 添加记录数
   */
  int add(WorkOrder workOrder);

  /**
   * 获取分页数据总量(获取某个客服可见:没人对此工单负责或本客服负责的工单)
   *
   * @param loginUserId 登录用户id
   * @param type        工单类型
   * @param status      工单状态
   * @param beginTime   开始时间
   * @param endTime     结束时间
   * @param keyword     关键词(当前为工单编号)
   * @return 分页数据总量
   */
  int getPaginationCount(@Param("loginUserId") String loginUserId,
                         @Param("type") Integer type,
                         @Param("status") Integer status,
                         @Param("beginTime") Timestamp beginTime,
                         @Param("endTime") Timestamp endTime,
                         @Param("keyword") String keyword);

  /**
   * 获取某个客服可见(没人对此工单负责或本客服负责的工单)的工单
   *
   * @param loginUserId 登录用户id
   * @param type        工单类型
   * @param status      工单状态
   * @param beginTime   开始时间
   * @param endTime     结束时间
   * @param keyword     关键词(当前为工单编号)
   * @param rowOffset   页偏移
   * @param pageSize    页大小
   * @return {@link Pagination<WorkOrderV1>}
   */
  List<WorkOrder> getPaginationList(@Param("loginUserId") String loginUserId,
                                    @Param("type") Integer type,
                                    @Param("status") Integer status,
                                    @Param("beginTime") Timestamp beginTime,
                                    @Param("endTime") Timestamp endTime,
                                    @Param("keyword") String keyword,
                                    @Param("rowOffset") Integer rowOffset,
                                    @Param("pageSize") Integer pageSize);

  /**
   * 更新工单信息
   *
   * @param workOrder {@link WorkOrder}
   * @return 更新记录数
   */
  int update(WorkOrder workOrder);

  /**
   * 查询某个顾客的工单
   *
   * @param customerId 顾客id
   * @param type       工单类型
   * @param status     工单状态
   * @param beginTime  过滤条件-开始时间
   * @param endTime    过滤条件-结束时间
   * @return {@link List<WorkOrderV1>}
   */
  List<WorkOrder> getCustomerWorkOrderList(@Param("customerId") String customerId,
                                           @Param("type") Integer type,
                                           @Param("status") Integer status,
                                           @Param("beginTime") Timestamp beginTime,
                                           @Param("endTime") Timestamp endTime);
}
