export default {
  // 配置显示在浏览器标签的title
  title: '爱情终是残念',
  // oauth认证所需的客户端信息
  client: 'YWRtaW5DbGllbnQ6MWEyYjNj',
  // @description refresh_token在localstorage中存储的时间，默认7天
  refreshTokenExpires: 604800,
  /**
   * @description 是否使用国际化，默认为false
   *              如果不使用，则需要在路由中给需要在菜单中展示的路由设置meta: {title: 'xxx'}
   *              用来在菜单中显示文字
   */
  useI18n: false,
  // api请求基础路径
  baseUrl: {
    // dev: 'http://web.frp.aqzscn.com/api/',
    dev: 'http://127.0.0.1:8520/api/',
    pro: 'http://api.aqzscn.com/'
    // pro: 'http://127.0.0.1:521/api/'
  },
  // 默认打开的首页的路由name值，默认为home
  homeName: 'home',
  // 需要加载的插件
  plugin: {
    'error-store': {
      showInHeader: true, // 设为false后不会在顶部显示错误日志徽标
      developmentOff: true // 设为true后在开发环境不会收集错误信息，方便开发中排查错误
    }
  }
}
