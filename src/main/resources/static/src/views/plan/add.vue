<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px" v-loading.body="loading">
      <el-form-item label="重要性">
        <el-select v-model="form.important">
          <el-option label="重要" value="1"></el-option>
          <el-option label="不重要" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="紧急性">
        <el-select v-model="form.urgent">
          <el-option label="紧急" value="1"></el-option>
          <el-option label="不紧急" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="计划分类">
        <el-select v-model="form.type">
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
      <el-form-item label="计划执行时间">
        <el-col :span="11">
          <el-date-picker
            v-model="form.beginAndEndTime"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['00:00:00', '23:59:59']">
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" v-model="form.note"></el-input>
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
  } from '../../api/plan'

  export default {
    data() {
      return {
        loading: false,
        form: {
          important: '1',
          urgent: '1',
          type: '1',
          beginAndEndTime: [],
          note: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.loading = true
        add(this.form.type, this.form.important === '1', this.form.urgent === '1',
          this.form.note, this.form.beginAndEndTime[0].getTime(), this.form.beginAndEndTime[1].getTime()).then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.loading = false
          this.$router.push({
            path: '/plan/index'
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
            path: '/plan/index'
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

