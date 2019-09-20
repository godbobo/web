import {
  getSeries,
  postSeries,
  putSeries
} from '@/api/blog/series'

export default {
  state: { },
  mutations: {},
  getters: {},
  actions: {
    // 获取连载列表
    getSeriesLst () {
      return getSeries().then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    },
    // 新增连载
    addSeries ({ commit }, series) {
      return postSeries(series).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    },
    // 修改连载
    updateSeries ({ commit }, series) {
      return putSeries(series).then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    }
  }
}
