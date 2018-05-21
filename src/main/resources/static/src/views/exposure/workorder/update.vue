<template>
  <div class="app-container">
    <el-main>
      <el-row :gutter="12">
        <el-col :gutter="12">
          <el-card shadow="always">
            <div slot="header" class="clearfix">
              <span><strong>工单信息</strong></span>
            </div>
            <el-form label-position="left" inline class="workorder">
              <h1>
                <el-form-item label="工单编号">
                  {{workorder.code}}
                </el-form-item>
              </h1>
              <h2>
                <el-form-item prop="created_at" label="类型">
                  <template slot-scope="scope">
                    <el-tag :type="workorder.type | typeColorFilter">
                      {{workorder.type | typeNameFilter}}
                    </el-tag>
                  </template>
                </el-form-item>
              </h2>
              <h2>
                <el-form-item prop="created_at" label="状态">
                  <template slot-scope="scope">
                    <el-tag :type="workorder.status | statusColorFilter">
                      {{workorder.status | statusNameFilter}}
                    </el-tag>
                  </template>
                </el-form-item>
              </h2>
              <h2>
                <el-form-item label="标题">
                  <template slot-scope="scope">
                    {{workorder.title}}
                  </template>
                </el-form-item>
              </h2>
              <el-form-item style="width: 100%">
                {{workorder.description}}
              </el-form-item>
            </el-form>
            <div class="bottom clearfix">
              <time class="time">{{ new Date(workorder.ctime).toLocaleString() }}</time>
              <el-button type="text" class="button" @click="cancelWorkOrder">撤销工单</el-button>
              <h1></h1>
              <el-button type="text" class="button" @click="sureFinishWorkOrder">确认结单</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="12" v-for="comment in workorder.workOrderCommentV1List" :key="comment.id">
        <el-col :gutter="12">
          <el-card shadow="always">
            <div slot="header" class="clearfix">
            <span>
              <strong>
                {{comment.customerId===null || comment.customerId.length>0?'顾客回复':'客服回复'}}
              </strong>
            </span>
            </div>
            <el-form label-position="left" inline class="workorder">
              <el-form-item style="width: 100%">
                {{comment.content}}
              </el-form-item>
            </el-form>
            <div class="bottom clearfix">
              <time class="time">{{ new Date(comment.ctime).toLocaleString() }}</time>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-form  :hidden="this.workorder.status | customerEnableComment">
        <el-form-item>
          <el-input type="textarea" v-model="comment"></el-input>
        </el-form-item>
        <el-form-item  align="right">
          <el-button type="primary" @click="onSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </div>
</template>

<style>
  .el-row {
    margin-bottom: 30px;
  }

  .workorder {
    font-size: 0;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .workorder label {
    width: 130px;
    color: black;
  }

  .workorder .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
</style>

<script>
  import {
    getById,
    addComment,
    updateStatus
  } from '../../../api/workorder'

  export default {
    data() {
      return {
        comment: '',
        source: 1,
        workorder: {
          id: '',
          customerId: '',
          dutyStaffId: '',
          code: '',
          title: '',
          type: '',
          description: '',
          finishTime: '',
          ctime: '',
          status: '',
          workOrderCommentV1List: []
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
      customerEnableComment(status) {
        const staffEnableCommentMap = {
          1: false,
          2: false,
          3: false,
          4: false,
          5: true,
          6: true,
          7: true
        }
        console.log(staffEnableCommentMap[status])
        return staffEnableCommentMap[status]
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
      if (this.$route.params.hasOwnProperty('id')) {
        this.workorder.id = this.$route.params['id']
      } else {
        this.$message({
          type: 'info',
          message: '无此用户信息'
        })
        this.$router.push({
          path: '/exposure/workorder/index'
        })
      }
      this.init()
    },
    methods: {
      init() {
        getById(this.workorder.id).then(response => {
          if (response.data === '') {
            this.$router.push({
              path: '/exposure/workorder/index'
            })
            this.$message({
              type: 'error',
              message: '工单不存在'
            })
          } else {
            this.workorder = response.data
            console.log(JSON.stringify(this.workorder))
          }
        }).catch((err) => {
          this.$message({
            type: 'error',
            message: err.response.data.message
          })
        })
      },
      onSubmit() {
        addComment(this.workorder.id, this.comment, this.source)
          .then(response => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.$router.push({
              path: '/exposure/workorder/index'
            })
          }).catch((err) => {
            this.$message({
              type: 'error',
              message: err.response.data.message
            })
          })
      },
      sureFinishWorkOrder() {
        this.$confirm('此操作会将工单变为已结单状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          if (this.workorder.status + '' !== '4') {
            this.$message({
              type: 'error',
              message: '只有待结单的订单才能结单!'
            })
            return
          }
          updateStatus(this.workorder.id, this.workorder.status, 7).then(response => {
            this.$message({
              type: 'success',
              message: '操作成功!'
            })
            this.$router.push({
              path: '/exposure/workorder/index'
            })
          })
        }).catch((err) => {
          this.$message({
            type: 'error',
            message: err.response.data.message
          })
        })
      },
      cancelWorkOrder() {
        this.$confirm('您要撤销此工单, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          if (this.workorder.status + '' === '6' || this.workorder.status + '' === '7' || this.workorder.status + '' === '5') {
            this.$message({
              type: 'error',
              message: '工单已完成不可结单!'
            })
            return
          }
          updateStatus(this.workorder.id, this.workorder.status, 6).then(response => {
            this.$message({
              type: 'success',
              message: '操作成功!'
            })
            this.$router.push({
              path: '/exposure/workorder/index'
            })
          })
        }).catch((err) => {
          this.$message({
            type: 'error',
            message: err.response.data.message
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

