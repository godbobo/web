import service from '@/api/dispatch/service'
import { apiResponse } from '@/libs/tools'

export default {
  state: {},
  mutations: {},
  getters: {},
  actions: {
    // 获取转发列表
    getDispatches ({ commit }, param) {
      return apiResponse(service.get, param)
    },
    // 新增转发服务
    postDispatches ({ commit }, param) {
      return apiResponse(service.post, param)
    },
    // 修改转发服务
    putDispatches ({ commit }, param) {
      return apiResponse(service.put, param)
    },
    // 删除服务
    deleteDispatches ({ commit }, param) {
      return apiResponse(service.delete, param, function (p) {
        if (!p) {
          throw new Error('请选择要删除的服务！')
        }
      })
    },
    // 开启服务
    enableDispatchService ({ commit }, param) {
      return apiResponse(service.enableService, param, function (p) {
        if (!p) {
          throw new Error('请选择要开启的服务！')
        }
      })
    }
  }
}
