<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchCondition" class="search-condition-form">
      <el-form-item label="等级">
        <el-select v-model="searchCondition.type" placeholder="用户分类" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="普通" value="1"></el-option>
          <el-option label="VIP" value="2"></el-option>
          <el-option label="SVIP" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchCondition.gender" placeholder="用户性别" @change="fetchData">
          <el-option label="全部" value=""></el-option>
          <el-option label="保密" value="0"></el-option>
          <el-option label="女" value="1"></el-option>
          <el-option label="男" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关键字">
        <el-input v-model="searchCondition.keyword" placeholder="顾客姓名/手机号" value=""></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="pagination.pageList" v-loading.body="listLoading" element-loading-text="Loading" fit
              highlight-current-row>
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="姓名">
              <span>{{ scope.row.name }}</span>
            </el-form-item>
            <el-form-item label="年龄">
              <span>{{ scope.row.age }}</span>
            </el-form-item>
            <el-form-item label="生日">
              <span>{{ new Date(scope.row.birthday).toLocaleDateString() }}</span>
            </el-form-item>
            <el-form-item label="性别">
              <span>{{scope.row.gender===0?'保密':scope.row.gender===1?'女':'男'}}</span>
            </el-form-item>
            <el-form-item label="手机号">
              <span>{{ scope.row.mobile }}</span>
            </el-form-item>
            <el-form-item label="电子邮箱">
              <span>{{ scope.row.email }}</span>
            </el-form-item>
            <el-form-item label="微信">
              <span>{{ scope.row.wechat }}</span>
            </el-form-item>
            <el-form-item label="QQ">
              <span>{{ scope.row.qq }}</span>
            </el-form-item>
            <el-form-item label="备注" class="note-el-form-item" style="width: 100%">
              <span>{{ scope.row.note }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center" label='#' width="30">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="姓名" width="80">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="性别" width="60">
        <template slot-scope="scope">
          <span>{{scope.row.gender===0?'保密':scope.row.gender===1?'女':'男'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          {{scope.row.mobile}}
        </template>
      </el-table-column>
      <el-table-column label="邮箱" align="center">
        <template slot-scope="scope">
          {{scope.row.email}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="等级">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type | typeFilter">
            {{scope.row.type === 1?'普通会员':scope.row.type === 2?'VIP':'SVIP'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="130">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
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
    <div style="margin-top: 20px">
      <el-button @click="toggleSelection([list[1], list[2]])">切换第二、第三行的选中状态</el-button>
      <el-button @click="toggleSelection()">取消选择</el-button>
    </div>
  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
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
  import { getPagination, del } from '../../api/customer'

  export default {
    data() {
      return {
        list: null,
        listLoading: true,
        multipleSelection: [],
        searchCondition: {
          keyword: null,
          type: null,
          gender: null
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
      typeFilter(status) {
        const statusMap = {
          1: 'success',
          2: 'gray',
          3: 'danger'
        }
        return statusMap[status]
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getPagination(this.searchCondition.type, this.searchCondition.gender, this.searchCondition.keyword, this.pagination.pageNo, this.pagination.pageSize).then(response => {
          this.pagination = response.data
          this.listLoading = false
        })
      },
      handleSizeChange(val) {
        console.log(val)
        this.pagination.pageSize = val
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.pagination.pageNo = val
        this.fetchData()
      },
      del(row) {
        this.$confirm('此操作将永久删除该顾客, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          del(row.id).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.fetchData()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTableXX.toggleRowSelection(row)
          })
        } else {
          this.$refs.multipleTableXX.clearSelection()
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      }
    }
  }
</script>
