import axios from '@/libs/api.request'

/**
 * 为请求方法添加CURD请求（适用于RESTFUL接口）
 * @param {*} obj 请求实体类
 * @param {*} url 请求地址
 */
export const addCurdRequest = (obj, url) => {
  // get方法
  obj.prototype.get = function ({ data, param }) {
    return axios.request({
      url: url,
      method: 'get',
      data: data,
      params: param
    })
  }
  // post方法
  obj.prototype.post = ({ data, param }) => {
    return axios.request({
      url: url,
      method: 'post',
      data: data,
      params: param
    })
  }
  // put方法
  obj.prototype.put = ({ data, param }) => {
    return axios.request({
      url: url,
      method: 'put',
      data: data,
      params: param
    })
  }
  // delete方法
  obj.prototype.delete = ({ data, param }) => {
    return axios.request({
      url: url,
      method: 'delete',
      data: data,
      params: param
    })
  }
}
