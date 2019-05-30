import Vue from 'vue'
import Router from 'vue-router'
import routes from './routers'
import store from '@/store'
import iView from 'iview'
import { setToken, getToken, canTurnTo, setTitle } from '@/libs/util'
import config from '@/config'
const { homeName } = config

Vue.use(Router)
const router = new Router({
  routes,
  mode: 'history'
})
const LOGIN_PAGE_NAME = 'login'

const turnTo = (to, access, next) => {
  if (canTurnTo(to.name, access, routes)) next() // 有权限，可访问
  else next({ replace: true, name: 'error_401' }) // 无权限，重定向到401页面
}

router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  const token = getToken()
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页 简单说就是强制未登录用户跳转到登录页
    next({
      name: LOGIN_PAGE_NAME // 跳转到登录页
    })
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    // 未登陆且要跳转的页面是登录页
    next() // 跳转
  } else if (token && to.name === LOGIN_PAGE_NAME) {
    // 已登录且要跳转的页面是登录页
    next({
      name: homeName // 跳转到homeName页
    })
  } else {
    if (store.state.user.hasGetInfo) {
      turnTo(to, store.state.user.access, next)
    } else {
      // 本地存的有token 但store中没有数据 对应登录后手动刷新的情况 这时local storage中的数据是完整的
      store.dispatch('getUserInfo').then(user => {
        // 拉取用户信息，通过用户权限和跳转的页面的name来判断是否有权限访问;access必须是一个数组，如：['super_admin'] ['super_admin', 'admin']
        turnTo(to, user.access, next)
      }).catch(() => {
        // 如果失败，有可能是access_token过期了 尝试先刷新token后再试
        setToken(null, null, null, true)
        store.dispatch('reLogin').then(res => {
          // 如果成功刷新 则再次获取用户信息
          if (res) {
            store.dispatch('getUserInfo').then(user => {
              turnTo(to, user.access, next)
            }).catch(() => {
              // 此时获取失败就不再做进一步的尝试了
              next({
                name: 'login'
              })
            })
          } else {
            // refresh_token无效 跳转到登录界面
            next({
              name: 'login'
            })
          }
        }).catch(() => {
          // 在刷新token时失败 同样不再尝试
          next({
            name: 'login'
          })
        })
      })
    }
  }
})

router.afterEach(to => {
  setTitle(to, router.app)
  // 在路由加载完成后关闭进度条动画，并将页面滑动到顶部
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router
