import axios from '@/libs/api.request'

// 新增博文
export const postArticles = formData => {
  return axios.request({
    url: 'blog/articles',
    method: 'post',
    data: formData
  })
}
