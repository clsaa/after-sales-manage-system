<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchCondition" class="search-condition-form">
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
      <el-form-item label="关键字">
        <el-input v-model="searchCondition.keyword" placeholder="请输入要搜索的内容" value="" v-on:input ="fetchData"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">
          <router-link :to="'/article/add'">新增</router-link>
        </el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="pagination.pageList" v-loading.body="listLoading"
              element-loading-text="Loading" fit
              highlight-current-row
              @selection-change="changed">
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="标题" style="width: 100%">
              <span>{{ scope.row.title }}</span>
            </el-form-item>
            <el-form-item label="创建时间" style="width: 40%">
              <span>{{ new Date(scope.row.ctime).toLocaleString() }}</span>
            </el-form-item>
            <el-form-item label="修改时间" style="width: 40%">
              <span>{{ new Date(scope.row.mtime).toLocaleString() }}</span>
            </el-form-item>
            <el-form-item label="正文" style="width: 100%">
              <div style="white-space: pre-wrap">
                {{scope.row.content}}
              </div>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center" label='#' width="30">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="类型" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type | typeColorFilter">
            {{scope.row.type | typeNameFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="left" label="标题" show-overflow-tooltip>
        <template slot-scope="scope">
          {{scope.row.title}}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="130">
        <template slot-scope="scope">
          <el-button type="text" size="small">
            <router-link :to="{path:'/article/'+scope.row.id}">编辑</router-link>
          </el-button>
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
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

</style>

<script>
  import {
    del,
    getPagination
  } from '../../api/article'

  export default {
    data() {
      return {
        list: null,
        listLoading: true,
        selectedList: [],
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
        getPagination(this.searchCondition.type, this.searchCondition.keyword, this.pagination.pageNo, this.pagination.pageSize).then(response => {
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
        this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
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
        }).catch((err) => {
          this.$message({
            type: 'error',
            message: err.response.data.message
          })
        })
      },
      changed(selection) {
        this.selectedList = selection
      }
    }
  }
</script>
