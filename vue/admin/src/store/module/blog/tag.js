import {
  getTags
} from '@/api/blog/tag'

export default {
  state: { },
  mutations: {},
  getters: {},
  actions: {
    // 获取文章
    getTagLst () {
      return getTags().then(data => {
        return data
      }).catch(error => {
        return Promise.reject(error)
      })
    }
  }
}
