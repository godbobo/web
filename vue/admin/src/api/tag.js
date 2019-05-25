import axios from '@/libs/api.request'

// 获取标签列表
export const getTags = () => {
  return axios.request({
    url: 'blog/tags',
    method: 'get'
  })
}
