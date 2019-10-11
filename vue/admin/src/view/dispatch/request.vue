<template>
  <div>
    <Card>
      <div class="search-wrap">
        <Input v-model="url" placeholder="请输入请求地址(api/后面的地址)" style="width: 50vw">
        <Select v-model="requestMethod" slot="prepend" style="width: 80px">
          <Option value="get">GET</Option>
          <Option value="post">POST</Option>
          <Option value="put">PUT</Option>
          <Option value="delete">DELETE</Option>
        </Select>
        <Button slot="append" type="primary" icon="ios-paper-plane" @click="handleSendRequest">发送</Button>
      </Input>
      </div>
      <Alert v-show="requestInfo" :type="showError ? 'error' : 'info'" class="margin-top">
        <template slot="desc">
          <div>
            <h3>请求信息</h3>
            <div class="margin-top">
              <pre>
                <code class="language-json">
                  {{requestInfo}}
                </code>
              </pre>
          </div>
          </div>
          <Divider />
          <div>
            <h3>状态码</h3>
            <div class="margin-top">{{resStatus}}</div>
          </div>
          <Divider />
          <div>
            <h3>返回数据</h3>
            <div class="margin-top">
              <pre>
                <code class="language-json">
                  {{resBody}}
                </code>
              </pre>
          </div>
          </div>
          <Divider />
          <div>
            <h3>响应头</h3>
            <div class="margin-top">
              <pre>
                <code class="language-json">
                  {{resHeader}}
                </code>
              </pre>
            </div>
          </div>
        </template>
    </Alert>
    </Card>
  </div>
</template>

<script>
import Prism from 'prismjs'
import axios from 'axios'
import beautify from 'json-beautify'
export default {
  name: 'request-page',
  data () {
    return {
      url: '',
      requestMethod: 'post',
      resBody: '',
      resStatus: '',
      resHeader: '',
      showError: false,
      requestInfo: ''
    }
  },
  computed: {
    baseUrl () {
      return process.env.NODE_ENV === 'development' ? this.$config.baseUrl.dev : this.$config.baseUrl.pro
    }
  },
  mounted () {
    // 添加请求拦截器
    axios.interceptors.request.use((config) => {
      this.requestInfo = beautify(config, null, 2, 100)
      return config
    }, function (error) {
      return Promise.reject(error)
    })
  },
  methods: {
    async handleSendRequest () {
      this.showError = false
      this.requestInfo = ''
      try {
        const config = {
          url: this.url,
          method: this.requestMethod,
          baseURL: this.baseUrl
        }
        const res = await axios.request(config)
        this.resBody = beautify(res.data, null, 2, 0)
        this.resStatus = res.status + res.statusText
        this.resHeader = beautify(res.headers, null, 2, 0)
      } catch (e) {
        console.error(e)
        const res = e.response
        this.resBody = beautify(res.data, null, 2, 0)
        this.resStatus = res.status + res.statusText
        this.resHeader = beautify(res.headers, null, 2, 0)
        this.showError = true
      } finally {
        setTimeout(() => {
          Prism.highlightAll()
        }, 300)
      }
    }
  }
}
</script>

<style lang="less" scoped>
.search-wrap {
  display: flex;
  justify-content: center;
  margin: 40px 0;
}
</style>
