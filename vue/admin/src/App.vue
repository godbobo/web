<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import { getToken } from '@/libs/util'

export default {
  name: 'App',
  mounted () {
    // 在5秒钟后执行是为了给其他请求加载数据的时间 保证token是可以读取到的
    if (getToken()) {
      this.checkToken()
    }
  },
  methods: {
    checkToken () {
      // 设置定时器自动刷新token 因为设置的误差时间是100秒 所以这里每40秒执行一次
      setInterval(() => {
        console.debug('开始检查token有效性...')
        // 获取access_token 如果已经过期就检查refresh_token 没有过期就结束本次执行
        const accessToken = getToken()
        if (accessToken && accessToken.expires > Date.parse(new Date())) {
        } else {
          this.$store.dispatch('reLogin').then(res => {
            console.debug(res ? '刷新token成功' : '刷新token失败')
          })
        }
      }, 40000)
    }
  }
}
</script>

<style lang="less">
.size{
  width: 100%;
  height: 100%;
}
html,body{
  .size;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
#app {
  .size;
}
</style>
