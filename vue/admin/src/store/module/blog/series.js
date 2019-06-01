import {
  getSeries
} from '@/api/blog/series'

export default {
  state: { },
  mutations: {},
  getters: {},
  actions: {
    // 获取文章
    getSeriesLst () {
      return getSeries().then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    }
  }
}
