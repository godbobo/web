import * as user from '@/api/user'
import { setToken, setRefreshToken, getRefreshToken } from '@/libs/util'
import { apiResponse } from '@/libs/tools'

// 重新设计 token
// 1 store存储access_token  localstorage存储refresh_token

export default {
  state: {
    userName: '',
    userId: '',
    avatarImgPath: '',
    access: '',
    hasGetInfo: false
  },
  mutations: {
    setAvatar (state, avatarPath) {
      state.avatarImgPath = avatarPath
    },
    setUserId (state, id) {
      state.userId = id
    },
    setUserName (state, name) {
      state.userName = name
    },
    setAccess (state, access) {
      state.access = access
    },
    setHasGetInfo (state, status) {
      state.hasGetInfo = status
    }
  },
  actions: {
    // 登录
    handleLogin ({ commit }, { userName, password }) {
      userName = userName.trim()
      return new Promise((resolve, reject) => {
        user.login({
          username: userName,
          password
        }).then(data => {
          // 响应内容应该包括access_token,refresh_token,expires,token_type,scope
          // 1 保存access_token
          setToken(data.access_token, data.expires_in, data.token_type)
          // 2 保存refresh_token
          setRefreshToken(data.refresh_token)
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 刷新token
    reLogin () {
      return new Promise((resolve, reject) => {
        const reToken = getRefreshToken()
        // 这里只判断是否存在的原因是要定义一个线程专门去解决token是否过期的问题
        if (!reToken) {
          resolve(false)
        } else if (parseInt(reToken.expires) < Date.parse(new Date())) {
          // 对于有token但已经过期的 删除token 结束运行
          setRefreshToken(null, true)
          resolve(false)
        }
        // 刷新token之前一定要确保localstorage中没有token，否则全局添加token的机制会替换掉刷新token特有的请求头
        setToken(null, null, null, true)
        user.refreshToken(reToken.token).then(data => {
          // 响应内容应该包括access_token,refresh_token,expires,token_type,scope
          // 1 保存access_token
          setToken(data.access_token, data.expires_in, data.token_type)
          // 2 保存refresh_token
          setRefreshToken(data.refresh_token)
          resolve(true)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出登录
    handleLogOut ({ state, commit }) {
      return new Promise((resolve, reject) => {
        setToken(null, null, null, true)
        setRefreshToken(null, true)
        commit('setAccess', [])
        resolve()
      })
    },
    // 获取用户相关信息
    getUserInfo ({ state, commit }) {
      return new Promise((resolve, reject) => {
        user.getUserInfo(state.token).then(data => {
          commit('setAvatar', data.headImg)
          commit('setUserName', data.realName)
          commit('setUserId', data.id)
          // 后台中角色列表是对象 这里需要改为字符串
          let accessLst = []
          data.authorities.map(val => {
            accessLst.push(val.authority)
          })
          // 接受用户数据的地方同样需要用到这个改装后的数组 因此将其加入data中
          data.access = accessLst
          commit('setAccess', accessLst)
          commit('setHasGetInfo', true)
          resolve(data)
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 添加用户
    addUser ({ commit }, param) {
      return apiResponse(user.addUser, param)
    },
    // 查询用户
    selectUser ({ commit }, param) {
      return apiResponse(user.selectUser, param)
    },
    // 编辑用户
    editUser ({ commit }, param) {
      return apiResponse(user.editUser, param)
    },
    // 获取角色列表
    getRoles () {
      return apiResponse(user.getRoles)
    },
    // 添加角色
    addRole ({ commit }, param) {
      return apiResponse(user.addRole, param)
    },
    // 根据用户id获取角色列表
    getRolesByUid ({ commit }, param) {
      return apiResponse(user.getRolesByUid, param)
    },
    // 修改用户角色
    updateUserRoles ({ commit }, param) {
      return apiResponse(user.updateUserRoles, param)
    }
  }
}
