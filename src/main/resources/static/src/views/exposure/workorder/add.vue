<template>

  <div class="app-container">
    <el-main>
      <el-form ref="form" :model="form" label-width="120px" v-loading.body="loading">
        <el-form-item label="分类">
          <el-select v-model="form.type" placeholder="计划分类" @change="fetchData">
            <el-option label="安装及环境" value="1"></el-option>
            <el-option label="功能使用" value="2"></el-option>
            <el-option label="账号类" value="3"></el-option>
            <el-option label="计费和财务" value="4"></el-option>
            <el-option label="备案及流程" value="5"></el-option>
            <el-option label="其他" value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标题">
          <el-input type="text" v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input type="text" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input type="text" v-model="form.mobile"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保存</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </div>
</template>

<script>
  import {
    add
  } from '../../../api/workorder'

  export default {
    data() {
      return {
        loading: false,
        form: {
          name: '',
          mobile: '',
          title: '',
          type: '1',
          description: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.loading = true
        add(this.form.name, this.form.mobile, this.form.title, this.form.type, this.form.description).then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.loading = false
          this.$router.push({
            path: '/exposure/workorder/index'
          })
        }).catch((err) => {
          this.loading = false
          this.$message({
            type: 'error',
            message: err.response.data.message
          })
        })
      },
      onCancel() {
        this.$confirm('填写的数据将被删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push({
            path: '/exposure/workorder/index'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '请继续操作'
          })
        })
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

