<template>
  <div>
    <div>
      <el-button @click="startHacking">Start</el-button>
      <el-button @click="startHacking">Start</el-button>
      <el-button @click="startHacking">Start</el-button>
      <el-button @click="startHacking">Start</el-button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    startHacking () {
      this.$notify({
        title: 'It works!',
        type: 'success',
        message: ' It\'s time for you to build something epic!',
        duration: 5000
      })
    }
  }
}
</script>

