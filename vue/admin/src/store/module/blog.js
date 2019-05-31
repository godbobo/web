import {
  getArticles
} from '@/api/article'

export default {
  state: { },
  mutations: {},
  getters: {},
  actions: {
    // 获取文章
    getArticleLst ({ commit }, { title, pageNum, pageSize }) {
      return getArticles({ title, pageNum, pageSize }).then(data => {
        return data
      }).catch(() => {})
    }
  }
}
