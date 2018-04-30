<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="姓名">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender">
          <el-option label="保密" value="0"></el-option>
          <el-option label="女" value="1"></el-option>
          <el-option label="男" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="级别">
        <el-select v-model="form.type">
          <el-option label="普通" value="1"></el-option>
          <el-option label="VIP" value="2"></el-option>
          <el-option label="SVIP" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item label="生日">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="Pick a date" v-model="form.birthday"
                          style="width: 100%;"></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.mobile"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="微信">
        <el-input v-model="form.wechat"></el-input>
      </el-form-item>
      <el-form-item label="QQ">
        <el-input v-model="form.qq"></el-input>
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
  } from '../../api/customer'

  export default {
    data() {
      return {
        form: {
          name: '',
          type: '1',
          age: 0,
          birthday: new Date('1970-01-01'),
          gender: '0',
          mobile: '',
          email: '',
          wechat: '',
          qq: '',
          note: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log(JSON.stringify(this.form))
        add(this.form.name, this.form.type, this.form.age, this.form.birthday.getTime(), this.form.gender,
          this.form.mobile, this.form.email, this.form.wechat, this.form.qq, this.form.note)
          .then(response => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.$router.push({
              path: '/customer/index'
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
            path: '/customer/index'
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

