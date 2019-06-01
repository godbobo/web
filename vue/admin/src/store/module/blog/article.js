import {
  getArticles,
  postArticles,
  deleteArticle,
  batchDeleteArticles,
  getArticleById
} from '@/api/blog/article'

export default {
  state: { },
  mutations: {},
  getters: {},
  actions: {
    // 获取文章
    getArticleLst ({ commit }, { title, pageNum, pageSize }) {
      return getArticles({ title, pageNum, pageSize }).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    },
    // 添加文章
    postArticle ({ commit }, article) {
      return postArticles(article).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    },
    // 删除文章
    deleteArticle ({ commit }, id) {
      return deleteArticle(id).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    },
    // 批量删除文章
    batchDeleteArticleLst ({ commit }, rows) {
      return new Promise((resolve, reject) => {
        if (!rows || rows.length === 0) {
          reject(new Error('主键列表不能为空'))
        }
        // 将列表转换为，分割的字符串
        let ids = ''
        rows.map(val => {
          ids += val.id + ','
        })
        batchDeleteArticles(ids.substring(0, ids.length - 1)).then(data => {
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 根据id查找文章
    getArticle ({ commit }, id) {
      return getArticleById(id).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    }
  }
}
