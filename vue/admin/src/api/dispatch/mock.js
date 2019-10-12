// import axios from '@/libs/api.request'
// import { convertQuery } from '@/libs/tools'
import { addCurdRequest } from '../base'

function Mock () {}

// 添加CURD方法
addCurdRequest(Mock, 'g/mocks')

const mock = new Mock()

export default mock
