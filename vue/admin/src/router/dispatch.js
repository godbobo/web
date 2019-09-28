import Main from '@/components/main'
export default {
  path: '/dispatch',
  name: 'dispatch',
  component: Main,
  meta: {
    icon: 'ios-nuclear',
    title: '中转服务'
  },
  children: [
    {
      path: 'request',
      name: 'request',
      meta: {
        icon: 'ios-bug',
        title: '请求测试'
      },
      component: () => import('@/view/dispatch/request.vue')
    },
    {
      path: 'list',
      name: 'list',
      meta: {
        icon: 'md-list',
        title: '转发列表'
      },
      component: () => import('@/view/dispatch/list.vue')
    },
    {
      path: 'mock',
      name: 'mock',
      meta: {
        icon: 'ios-barcode',
        title: '模拟数据'
      },
      component: () => import('@/view/dispatch/mock.vue')
    }
  ]
}
