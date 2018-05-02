<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchCondition" class="search-condition-form">
      <div align="left">
        <el-form-item label="分类">
          <el-select v-model="searchCondition.type" placeholder="计划分类" @change="fetchData">
            <el-option label="全部" value=""></el-option>
            <el-option label="安装及环境" value="1"></el-option>
            <el-option label="功能使用" value="2"></el-option>
            <el-option label="账号类" value="3"></el-option>
            <el-option label="计费和财务" value="4"></el-option>
            <el-option label="备案及流程" value="5"></el-option>
            <el-option label="其他" value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchCondition.status" placeholder="工单状态" @change="fetchData">
            <el-option label="全部" value=""></el-option>
            <el-option label="未处理" value="1"></el-option>
            <el-option label="处理中" value="2"></el-option>
            <el-option label="待补充" value="3"></el-option>
            <el-option label="待确认结单" value="4"></el-option>
            <el-option label="待评价" value="5"></el-option>
            <el-option label="已撤销" value="6"></el-option>
            <el-option label="已结单" value="7"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <div align="left">
        <el-form-item label="时间" block="block">
          <el-col :span="11">
            <el-date-picker
              v-model="searchCondition.beginAndEndTime"
              type="daterange"
              unlink-panels
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:59']"
              :picker-options="pickerOptions">
            </el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="工单编号">
          <el-input v-model="searchCondition.keyword" placeholder="请输入工单编号" value=""></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchData">查询</el-button>
        </el-form-item>
      </div>
    </el-form>
    <el-table ref="multipleTable" :data="pagination.pageList" v-loading.body="listLoading"
              element-loading-text="Loading" fit
              highlight-current-row>
      <el-table-column align="center" label='#' width="30">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="工单编号" width="160">
        <template slot-scope="scope">
          {{scope.row.code}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type | typeColorFilter">
            {{scope.row.type | typeNameFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" show-overflow-tooltip width="150">
        <template slot-scope="scope">
          {{scope.row.title}}
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" show-overflow-tooltip width="350">
        <template slot-scope="scope">
          {{scope.row.description}}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" width="180">
        <template slot-scope="scope">
          {{ new Date(scope.row.ctime).toLocaleString() }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusColorFilter">
            {{scope.row.status | statusNameFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="80">
        <template slot-scope="scope">
          <el-button type="text" size="small"><router-link :to="'/workorder/'+scope.row.id">处理</router-link></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block" align="right" v-model="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.pageNo"
        :page-sizes="[10, 25, 50, 100]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.totalCount">
      </el-pagination>
    </div>
  </div>
</template>

<style>
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>

<script>
  import {
    getPagination
  } from '../../api/workorder'

  export default {
    data() {
      return {
        listLoading: true,
        searchCondition: {
          type: null,
          status: null,
          beginAndEndTime: [],
          keyword: null
        },
        pagination: {
          pageList: null,
          pageSize: 10,
          pageNo: 1,
          totalCount: 0,
          rowOffset: 0,
          firstPage: true,
          lastPage: true,
          prePage: 1,
          totalPage: 1,
          nextPage: 1
        },
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(new Date(new Date().toLocaleDateString()).getTime() - 3600 * 1000 * 24 * 7)
              end.setTime(new Date(new Date().toLocaleDateString()).getTime() + (3600 * 1000 * 24 - 1))
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(new Date(new Date().toLocaleDateString()).getTime() - 3600 * 1000 * 24 * 30)
              end.setTime(new Date(new Date().toLocaleDateString()).getTime() + (3600 * 1000 * 24 - 1))
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(new Date(new Date().toLocaleDateString()).getTime() - 3600 * 1000 * 24 * 90)
              end.setTime(new Date(new Date().toLocaleDateString()).getTime() + (3600 * 1000 * 24 - 1))
              picker.$emit('pick', [start, end])
            }
          }]
        }
      }
    },
    filters: {
      statusNameFilter(status) {
        const statusNameMap = {
          1: '未处理',
          2: '处理中',
          3: '待补充',
          4: '待确认结单',
          5: '待评价',
          6: '已撤销',
          7: '已结单'
        }
        return statusNameMap[status]
      },
      statusColorFilter(status) {
        const statusColorMap = {
          1: 'danger',
          2: 'danger',
          3: 'warning',
          4: 'warning',
          5: 'success',
          6: 'info',
          7: 'info'
        }
        return statusColorMap[status]
      },
      typeNameFilter(type) {
        const typeMap = {
          1: '安装及环境',
          2: '功能使用',
          3: '账号类',
          4: '计费和财务',
          5: '备案及流程',
          6: '其他'
        }
        return typeMap[type]
      },
      typeColorFilter(type) {
        const typeColorMap = {
          1: 'success',
          2: 'success',
          3: 'success',
          4: 'success',
          5: 'success',
          6: 'success'
        }
        return typeColorMap[type]
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getPagination(this.searchCondition.type, this.searchCondition.status, this.searchCondition.beginTime,
          this.searchCondition.endTime, this.searchCondition.keyword, this.pagination.pageNo, this.pagination.pageSize).then(response => {
          this.pagination = response.data
          this.listLoading = false
        }).catch((err) => {
          this.listLoading = false
          this.$message({
            type: 'error',
            message: err.response.data.message
          })
        })
      },
      handleSizeChange(val) {
        this.pagination.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.pagination.pageNo = val
        this.fetchData()
      }
    }
  }
</script>
