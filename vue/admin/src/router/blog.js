import Main from '@/components/main'
export default {
  path: '/blog',
  name: 'blog',
  component: Main,
  meta: {
    icon: 'md-cafe',
    title: '博客管理'
  },
  children: [
    {
      path: 'edit',
      name: 'edit',
      meta: {
        icon: 'md-brush',
        title: '编辑'
      },
      component: () => import('@/view/blog/edit.vue')
    },
    {
      path: 'article',
      name: 'article',
      meta: {
        icon: 'md-book',
        title: '博文管理'
      },
      component: () => import('@/view/blog/article.vue')
    },
    {
      path: 'series',
      name: 'series',
      meta: {
        icon: 'logo-buffer',
        title: '连载管理'
      },
      component: () => import('@/view/blog/series.vue')
    }
  ]
}
