import axios from 'axios'
import store from '@/store'
import { Message } from 'iview'
import { getToken } from './util'
import https from 'https'
// import { Spin } from 'iview'

// 用于记录错误日志
const addErrorLog = errorInfo => {
  const { status, request: { responseURL }, config: { method } } = errorInfo
  // 截取错误信息
  let et = ''
  errorInfo.data.errors.map(val => {
    et += val.title + ','
  })
  et = et.substr(0, et.length - 1)
  let info = {
    type: method,
    code: status,
    mes: et,
    url: responseURL
  }
  if (!responseURL.includes('save_error_logger')) store.dispatch('addErrorLog', info)
}

class HttpRequest {
  constructor (baseUrl = baseURL) {
    this.baseUrl = baseUrl
    this.queue = {}
  }
  getInsideConfig () {
    const config = {
      baseURL: this.baseUrl,
      headers: {
        // 'Content-Type': 'application/x-www-form-urlencoded'
      },
      httpsAgent: new https.Agent({
        rejectUnauthorized: false
      })
    }
    return config
  }
  destroy (url) {
    delete this.queue[url]
    if (!Object.keys(this.queue).length) {
      // Spin.hide()
    }
  }
  interceptors (instance, url) {
    // 请求拦截
    instance.interceptors.request.use(config => {
      // 添加全局的loading...
      if (!Object.keys(this.queue).length) {
        // Spin.show() // 不建议开启，因为界面不友好
      }
      // 如果存在 token，则自动将其添加到请求头
      const localToken = getToken()
      console.debug('请求地址为：', url, ' 携带token为：', localToken ? localToken.token : '')
      if (localToken) {
        config.headers['Authorization'] = `${localToken.type} ${localToken.token}`
      }
      this.queue[url] = true
      return config
    }, error => {
      return Promise.reject(error)
    })
    // 响应拦截
    instance.interceptors.response.use(res => {
      this.destroy(url)
      const { data, status } = res
      console.debug('从', url, '获得响应，响应码为：', status, ' 响应内容为：', JSON.stringify(data))
      // 如果响应中存在需要发送给用户的信息，则在这里拦截并显示
      if (data.msg) {
        Message.success({
          content: data.msg
        })
      }
      // 如果能找到标准结构的响应，则提取数据后返回，否则返回完整的响应内容
      return data.data ? data.data : data
    }, error => {
      this.destroy(url)
      let errorInfo = error.response
      console.debug('请求', url, '失败，错误信息为：', errorInfo ? JSON.stringify(errorInfo.data) : '')
      if (!errorInfo) {
        const { request: { statusText, status }, config } = JSON.parse(JSON.stringify(error))
        errorInfo = {
          statusText,
          status,
          request: { responseURL: config.url }
        }
      } else { // 存在时提示用户错误信息
        Message.error({
          content: errorInfo.data.msg || `${errorInfo.data.status} ${errorInfo.data.error} 请求${errorInfo.data.path}失败,${errorInfo.data.message}`
        })
      }
      addErrorLog(errorInfo)
      return Promise.reject(error)
    })
  }
  request (options) {
    const instance = axios.create()
    options = Object.assign(this.getInsideConfig(), options)
    this.interceptors(instance, options.url)
    return instance(options)
  }
}
export default HttpRequest
