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
export const getArticles = ({ title, pageNum, pageSize, queryTypes }) => {
  const params = {
    ...convertQuery({ pageNum, pageSize }, 'pageRequest')
    // 'pageRequest.pageNum': pageNum,
    // 'pageRequest.pageSize': pageSize
  }
  if (title) {
    params.title = title
  }
  // 只有在传入请求类型时才加上该参数
  if (queryTypes) {
    params.queryType = queryTypes
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

/**
 * 修改文章和话题的映射关系
 * @param {Number} aid 文章主键
 * @param {Number} sid 话题主键
 */
export const putArticleSeriesMap = (aid, sid) => {
  const params = {
    aId: aid,
    sId: sid
  }
  return axios.request({
    url: 'blog/article-series-map',
    method: 'put',
    params
  })
}
