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
 * 根据id删除文章
 * @param {String} id 文章主键
 */
export const deleteArticle = (id) => {
  return axios.request({
    url: 'blog/articles/' + id,
    method: 'delete'
  })
}

/**
 * 根据id列表批量删除文章
 * @param {String} ids id列表
 */
export const batchDeleteArticles = (ids) => {
  const params = {
    idStr: ids
  }
  return axios.request({
    url: 'blog/articles',
    method: 'delete',
    params
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

/**
 * 根据Id查询文章详情
 * @param {Number} id 文章id
 */
export const getArticleById = id => {
  return axios.request({
    url: 'blog/articles/' + id,
    method: 'get'
  })
}
