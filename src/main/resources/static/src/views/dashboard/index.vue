<template>
  <div>
    <el-row :gutter="24" :style="{marginTop: '80px'}">
      <el-col :span="13" :offset="1">
        <div id="myChart" :style="{width: '100%', height: '450px'}"></div>
      </el-col>
      <el-col :span="10">
        <div id="myChart2" :style="{width: '100%', height: '450px'}"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: 'hello',
    data() {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    mounted() {
      this.drawLine()
      this.drawPie();
    },
    methods: {
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        const myChart = echarts.init(document.getElementById('myChart'))
        const option = {
          title: {
            text: '个人计划完成情况',
            subtext: '请合理安排时间',
            left: 'center',
          },
          color: ['rgba(48, 65, 86, 0.5)'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '10%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: ['重要计划', '紧急计划', '未完成计划', '近七天完成', '个人计划'],
              name: '计划类型',
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              name: '个',
              type: 'value'
            }
          ],
          series: [
            {
              name: '直接访问',
              type: 'bar',
              barWidth: '80%',
              data: [7, 4, 9, 45, 34]
            }
          ]
        }
        // 绘制图表
        myChart.setOption(option)
      },
      drawPie() {
        // 基于准备好的dom，初始化echarts实例
        const myChart2 = echarts.init(document.getElementById('myChart2'))
        const option = {

          title: {
            text: '工单处理',
            left: 'center',
          },

          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },

          visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
              colorLightness: [0, 1]
            }
          },
          series: [
            {
              name: '数量',
              type: 'pie',
              radius: '78%',
              center: ['50%', '50%'],
              data: [
                {value: 335, name: '待补充'},
                {value: 210, name: '处理中'},
                {value: 274, name: '待确认'},
                {value: 135, name: '已结单'},
                {value: 312, name: '未处理'},
                {value: 400, name: '待评价'}
              ].sort(function (a, b) {
                return a.value - b.value;
              }),
              roseType: 'radius',
              label: {
                normal: {
                  textStyle: {
                    color: 'rgba(0, 0, 0, 0.7)'
                  }
                }
              },
              labelLine: {
                normal: {
                  lineStyle: {
                    color: 'rgba(0, 0, 0, 0.4)'
                  },
                  smooth: 0.2,
                  length: 10,
                  length2: 20
                }
              },
              itemStyle: {
                normal: {
                  color: 'rgba(48, 65, 86, 0.5)',
                  shadowBlur: 40,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },

              animationType: 'scale',
              animationEasing: 'elasticOut',
              animationDelay: function (idx) {
                return Math.random() * 200;
              }
            }
          ]
        };
        // 绘制图表
        myChart2.setOption(option)
      }
    }
  }
</script>
