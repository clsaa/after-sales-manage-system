<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
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
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea" v-model="form.content" :rows="12"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    add
  } from '../../api/article'

  export default {
    data() {
      return {
        form: {
          type: '1',
          title: '',
          content: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log(JSON.stringify(this.form))
        add(this.form.type, this.form.title, this.form.content)
          .then(response => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.$router.push({
              path: '/article/index'
            })
          }).catch((err) => {
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
            path: '/article/index'
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

</style>

