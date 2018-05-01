<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchCondition" class="search-condition-form">
      <el-form-item label="分类">
        <el-select v-model="searchCondition.type" placeholder="计划分类" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="下次联系计划" value="1"></el-option>
          <el-option label="回访计划" value="2"></el-option>
          <el-option label="上级任务计划" value="3"></el-option>
          <el-option label="合同到期计划" value="4"></el-option>
          <el-option label="回款计划" value="5"></el-option>
          <el-option label="收款计划" value="6"></el-option>
          <el-option label="客户生日计划" value="7"></el-option>
          <el-option label="预约计划" value="8"></el-option>
          <el-option label="个人计划" value="9"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="完成度">
        <el-select v-model="searchCondition.status" placeholder="是否完成" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="已完成" value="2"></el-option>
          <el-option label="待完成" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="重要性">
        <el-select v-model="searchCondition.important" placeholder="重要性" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="重要" value="1"></el-option>
          <el-option label="不重要" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="紧急性">
        <el-select v-model="searchCondition.urgent" placeholder="紧急性" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="紧急" value="1"></el-option>
          <el-option label="不紧急" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">
          <router-link :to="'/plan/add'">新增</router-link>
        </el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="pagination.pageList" v-loading.body="listLoading"
              element-loading-text="Loading" fit
              highlight-current-row
              :row-class-name="tableRowClassName">
      <el-table-column align="center" label='#' width="30">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="分类" width="80">
        <template slot-scope="scope">
          {{scope.row.type | typeFilter}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="重要性" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.important | importantOrUrgentFilter">
            {{scope.row.important === true?'重要':'不重要'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="紧急性" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.urgent | importantOrUrgentFilter">
            {{scope.row.urgent === true?'紧急':'不紧急'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" show-overflow-tooltip width="200">
        <template slot-scope="scope">
          {{scope.row.note}}
        </template>
      </el-table-column>
      <!--<el-table-column label="开始时间" align="center" width="180">-->
        <!--<template slot-scope="scope">-->
          <!--{{ new Date(scope.row.beginTime).toLocaleString() }}-->
        <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column label="计划截至时间" align="center" width="180">
        <template slot-scope="scope">
          {{ new Date(scope.row.endTime).toLocaleString()  }}
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" width="180">
        <template slot-scope="scope">
          {{ scope.row.status === 2 ?new Date(scope.row.finishTime).toLocaleString() :'' }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">
            {{scope.row.status === 1?'待完成':scope.row.status === 2?'已完成':'已超时'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="130">
        <template slot-scope="scope">
          <el-button @click="finish(scope.row)" type="text" size="small" v-bind:disabled="scope.row.status === 2">完成</el-button>
          <el-button @click="del(scope.row)" type="text" size="small">删除</el-button>
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

  .el-table .overtime-row {
    background: oldlace;
  }

  .el-table .todo-row {
    background: #f0f9eb;
  }

  .el-table .finish-row {
    background: lightgray;
  }
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
    getPagination,
    del,
    finish
  } from '../../api/plan'

  export default {
    data() {
      return {
        listLoading: true,
        searchCondition: {
          type: null,
          status: null,
          important: null,
          urgent: null
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
        }
      }
    },
    filters: {
      importantOrUrgentFilter(important) {
        const importantOrUrgentMap = {
          1: 'danger',
          0: 'success',
          true: 'danger',
          false: 'success'
        }
        return importantOrUrgentMap[important]
      },
      statusFilter(status) {
        const statusMap = {
          1: 'success',
          2: 'gray',
          3: 'danger'
        }
        return statusMap[status]
      },
      typeFilter(type) {
        const typeMap = {
          1: '下次联系计划',
          2: '回访计划',
          3: '上级任务计划',
          4: '合同到期计划',
          5: '回款计划',
          6: '收款计划',
          7: '客户生日计划',
          8: '预约计划',
          9: '个人计划'
        }
        return typeMap[type]
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getPagination(this.searchCondition.type, this.searchCondition.important, this.searchCondition.urgent,
          this.searchCondition.status, this.pagination.pageNo, this.pagination.pageSize).then(response => {
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
      },
      del(row) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          del(row.id).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.fetchData()
          }).catch((err) => {
            this.$message({
              type: 'error',
              message: err.response.data.message
            })
          })
        })
      },
      finish(row) {
        this.$confirm('将该任务转为已完成状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          finish(row.id).then(response => {
            this.$message({
              type: 'success',
              message: '更新成功!'
            })
            this.fetchData()
          }).catch((err) => {
            this.listLoading = false
            this.$message({
              type: 'error',
              message: err.response.data.message
            })
          })
        })
      },
      tableRowClassName({ row, rowIndex }) {
        if (row.status === 1) {
          return 'todo-row'
        }
        if (row.status === 2) {
          return 'finish-row'
        }
        if (row.status === 3) {
          return 'overtime-row'
        }
        return ''
      }
    }
  }
</script>
