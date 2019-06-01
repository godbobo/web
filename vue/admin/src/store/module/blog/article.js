import {
  getArticles,
  postArticles
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
    }
  }
}
