import axios from '@/libs/api.request'
import { convertQuery } from '@/libs/tools'

// 新增博文
export const postArticles = formData => {
  return axios.request({
    url: 'blog/articles',
    method: 'post',
    data: formData
  })
}

/**
 * 根据条件查询文章列表
 * @param {*} param0 文章标题
 */
export const getArticles = ({ title, pageNum, pageSize }) => {
  const params = {
    title,
    ...convertQuery({ pageNum, pageSize }, 'pageRequest')
    // 'pageRequest.pageNum': pageNum,
    // 'pageRequest.pageSize': pageSize
  }
  return axios.request({
    url: 'blog/articles',
    method: 'get',
    params
  })
}
