package com.clsaa.ms.hermes.dao;


import com.clsaa.ms.hermes.entity.po.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任贵杰
 * @version v1
 * @summary 计划DAO
 * @since 2018-04-29
 */
public interface PlanDao {

  /**
   * 根据id查询计划信息
   *
   * @param id 计划id
   * @return {@link Plan}对象
   */
  Plan getById(@Param("id") String id);

  /**
   * 添加计划
   *
   * @param plan {@link Plan}
   * @return 添加记录数
   */
  int add(Plan plan);

  /**
   * 修改计划状态
   *
   * @param id       计划id
   * @param statusTo 计划状态
   * @param muser    muser
   * @return 更改记录条数
   */
  int updateStatus(@Param("id") String id,
                   @Param("statusTo") Integer statusTo,
                   @Param("muser") String muser);

  /**
   * 查询符合分页条件的数据量
   *
   * @param cuser     创建人
   * @param type      计划类型
   * @param important 是否重要
   * @param urgent    是否紧急
   * @param status    状态
   * @return 符合条件的数据量
   */
  int getPaginationCount(@Param("cuser") String cuser,
                         @Param("type") Integer type,
                         @Param("important") Integer important,
                         @Param("urgent") Integer urgent,
                         @Param("status") Integer status);

  /**
   * 分页查询计划
   *
   * @param cuser     创建人
   * @param type      计划类型
   * @param important 是否重要
   * @param urgent    是否紧急
   * @param status    状态
   * @param rowOffset 偏移量
   * @param pageSize  页大小
   * @return {@link List<Plan>}对象
   */
  List<Plan> getPagination(@Param("cuser") String cuser,
                           @Param("type") Integer type,
                           @Param("important") Integer important,
                           @Param("urgent") Integer urgent,
                           @Param("status") Integer status,
                           @Param("rowOffset") Integer rowOffset,
                           @Param("pageSize") Integer pageSize);
}
