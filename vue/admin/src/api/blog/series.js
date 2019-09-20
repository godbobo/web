import axios from '@/libs/api.request'

// 获取连载列表
export const getSeries = () => {
  return axios.request({
    url: 'blog/series',
    method: 'get'
  })
}

// 新增连载
export const postSeries = (series) => {
  return axios.request({
    url: 'blog/series',
    method: 'post',
    data: series
  })
}

// 更新连载
export const putSeries = (series) => {
  return axios.request({
    url: 'blog/series',
    method: 'put',
    data: series
  })
}
