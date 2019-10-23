import axios from '@/libs/api.request'
import config from '@/config'

/**
 * 登录操作 获取token
 * @param {String, String} 用户名 密码
 */
export const login = ({ username, password }) => {
  // 组装认证参数
  const params = {
    grant_type: 'password',
    scope: 'all',
    username,
    password
  }
  return axios.request({
    headers: {
      'Authorization': 'Basic ' + config.client
    },
    url: 'oauth/token',
    params,
    method: 'post'
  })
}

/**
 * 刷新token
 * @param {*} refresh_token
 */
export const refreshToken = refresh_token => {
  // 组装认证参数
  const params = {
    grant_type: 'refresh_token',
    refresh_token
  }
  return axios.request({
    headers: {
      'Authorization': 'Basic ' + config.client
    },
    url: 'oauth/token',
    params,
    method: 'post'
  })
}

/**
 * 获取用户信息 token会在请求工具类中自动封装
 */
export const getUserInfo = () => {
  return axios.request({
    url: 'g/userinfo',
    method: 'get'
  })
}

/**
 * 添加用户
 * @param {*} email 邮箱
 * @param {*} password 密码
 * @param {*} username 昵称
 * @param {*} realName 姓名
 * @param {*} sign 签名
 * @param {*} gender 性别
 * @param {*} headImg 头像
 * @param {*} tel 电话
 */
export const addUser = ({ email = '', password = '', username = '', realName = '', sign = '', gender = '', headImg = '', tel = '' }) => {
  return axios.request({
    url: 'g/users',
    method: 'post',
    data: {
      email, password, username, realName, sign, gender, headImg, tel
    }
  })
}

// 查询用户
export const selectUser = ({ email = '', password = '', username = '', realName = '', sign = '', gender = '', headImg = '', tel = '', pageNum, pageSize }) => {
  return axios.request({
    url: 'g/users',
    method: 'get',
    params: {
      email, password, username, realName, sign, gender, headImg, tel, pageNum, pageSize
    }
  })
}

// 修改用户信息
export const editUser = ({ id, email = '', password = '', username = '', realName = '', sign = '', gender = '', headImg = '', tel = '', enabled }) => {
  return axios.request({
    url: 'g/users',
    method: 'put',
    data: {
      id, email, password, username, realName, sign, gender, headImg, tel, enabled
    }
  })
}

// 获取角色列表
export const getRoles = () => {
  return axios.request({
    url: 'g/roles',
    method: 'get'
  })
}

// 添加角色
export const addRole = ({ name, remark }) => {
  return axios.request({
    url: 'g/roles',
    method: 'post',
    data: {
      name, remark
    }
  })
}

// 根据用户id获取已有角色
export const getRolesByUid = ({ uid }) => {
  return axios.request({
    url: `g/roles/${uid}`,
    method: 'get'
  })
}

// 修改用户角色
export const updateUserRoles = ({ id, roleName }) => {
  return axios.request({
    url: `g/user/roles`,
    method: 'put',
    data: { id, roleName }
  })
}
