import Vue from 'vue'
import Vuex from 'vuex'

import user from './module/user'
import blogArticle from './module/blog/article'
import blogTag from './module/blog/tag'
import blogSeries from './module/blog/series'
import dispatchService from './module/dispatch/service'
import dispatchMock from './module/dispatch/mock'
import app from './module/app'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //
  },
  mutations: {
    //
  },
  actions: {
    //
  },
  modules: {
    user,
    app,
    blogArticle,
    blogSeries,
    blogTag,
    dispatchService,
    dispatchMock
  }
})
