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

export const logout = (token) => {
  return axios.request({
    url: 'logout',
    method: 'post'
  })
}

export const getMessage = () => {
  return axios.request({
    url: 'message/init',
    method: 'get'
  })
}

export const getContentByMsgId = msg_id => {
  return axios.request({
    url: 'message/content',
    method: 'get',
    params: {
      msg_id
    }
  })
}

export const hasRead = msg_id => {
  return axios.request({
    url: 'message/has_read',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const removeReaded = msg_id => {
  return axios.request({
    url: 'message/remove_readed',
    method: 'post',
    data: {
      msg_id
    }
  })
}

export const restoreTrash = msg_id => {
  return axios.request({
    url: 'message/restore',
    method: 'post',
    data: {
      msg_id
    }
  })
}
