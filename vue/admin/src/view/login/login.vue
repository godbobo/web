<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form :isLoading="isLoading" @on-success-valid="handleSubmit"></login-form>
          <p class="login-tip">输入任意用户名和密码即可</p>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
export default {
  components: {
    LoginForm
  },
  data () {
    return {
      isLoading: false
    }
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    handleSubmit ({ userName, password }) {
      this.isLoading = true
      this.handleLogin({ userName, password }).then(() => {
        this.getUserInfo().then(() => {
          this.isLoading = false
          this.$Message.success({
            content: '登录成功'
          })
          this.$router.push({
            name: this.$config.homeName
          })
        })
      }).catch(() => {
        this.isLoading = false
        this.$Message.error({
          content: '连接失败'
        })
      })
    }
  }
}
</script>

<style>

</style>
