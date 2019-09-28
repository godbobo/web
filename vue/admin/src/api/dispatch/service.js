import axios from '@/libs/api.request'
// import { convertQuery } from '@/libs/tools'
import { addCurdRequest } from '../base'

function Service () {}

// 开启转发服务
Service.prototype.enableService = (id) => {
  return axios.request({
    url: 'g/dispatch-status',
    method: 'put',
    data: {
      id: id
    }
  })
}

// 添加CURD方法
addCurdRequest(Service, 'g/dispatches')

const service = new Service()

export default service
