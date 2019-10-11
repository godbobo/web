import mock from '@/api/dispatch/mock'
import { apiResponse } from '@/libs/tools'

export default {
  state: {},
  mutations: {},
  getters: {},
  actions: {
    // 获取转发列表
    getMocks ({ commit }, param) {
      return apiResponse(mock.get, param)
    },
    // 新增转发服务
    postMocks ({ commit }, param) {
      return apiResponse(mock.post, param)
    },
    // 修改转发服务
    putMocks ({ commit }, param) {
      return apiResponse(mock.put, param)
    },
    // 删除服务
    deleteMocks ({ commit }, param) {
      return apiResponse(mock.delete, param, function (p) {
        if (!p) {
          throw new Error('请选择要删除的模拟数据！')
        }
      })
    }
  }
}
