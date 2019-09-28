<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted () {}
}
</script>

<style lang="less">
.size {
  width: 100%;
  height: 100%;
}
html,
body {
  .size;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
#app {
  .size;
}

// 水平居中
.ha-center {
  text-align: center;
  margin: 0 auto;
}
</style>

<style>
@import url("//at.alicdn.com/t/font_1439875_r3qj94cwcag.css");
.margin-top {
  margin-top: 10px;
}
.margin-h {
  margin: 0 10px;
}
</style>
