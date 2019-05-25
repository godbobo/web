import axios from 'axios'
import store from '@/store'
import { Message } from 'iview'
// import { Spin } from 'iview'

// 用于记录错误日志
const addErrorLog = errorInfo => {
  console.log(errorInfo)
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
      }
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
      this.queue[url] = true
      return config
    }, error => {
      return Promise.reject(error)
    })
    // 响应拦截
    instance.interceptors.response.use(res => {
      this.destroy(url)
      const { data, status } = res
      if (data.msg) {
        Message.success({
          content: data.msg
        })
      }
      return { data, status }
    }, error => {
      this.destroy(url)
      let errorInfo = error.response
      if (!errorInfo) {
        const { request: { statusText, status }, config } = JSON.parse(JSON.stringify(error))
        errorInfo = {
          statusText,
          status,
          request: { responseURL: config.url }
        }
      } else { // 存在时提示用户错误信息
        Message.error({
          content: errorInfo.data.msg + ' 详细信息请查看日志'
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
