import axios from '@/libs/api.request'

// 获取连载列表
export const getSeries = () => {
  return axios.request({
    url: 'blog/series',
    method: 'get'
  })
}
