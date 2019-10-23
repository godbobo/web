<template>
  <Card shadow class="table-wrap">
    <p slot="title"><Icon type="md-book"/> 文章列表</p>
    <Table highlight-row :columns="tableHeaderDefine" :loading="isTableLoading" :data="data" @on-selection-change="handleSelectionChange">
      <div slot="header" class="table-tools-wrap">
        <Button :disabled="!canDoBatch" class="item-margin" type="warning" icon="md-trash" @click="handleDeleteSelection">批量删除</Button>
        <Button :disabled="!canDoBatch" class="item-margin" type="info" icon="ios-download" @click="handleExportSelection">批量导出</Button>
      </div>
      <Page slot="footer" :current.sync="params.pageNum" :page-size="params.pageSize" :total="rowTotal" show-elevator show-total @on-change="handlePageChange" />
    </Table>
    <Drawer :title="articleObj.title" placement="left" :width="50" :closable="false" v-model="drawerVisiable">
      <md-viewer :content="articleObj.content"/>
    </Drawer>
  </Card>
</template>

<script>
import { mapActions } from 'vuex'
import { getRelativeTime } from '@/libs/tools'
import MdViewer from '_c/md/viewer'
import config from '@/config'

export default {
  name: 'BlogArticlePage',
  components: {
    MdViewer
  },
  data () {
    return {
      params: {
        title: '',
        pageNum: 1,
        pageSize: 20
      },
      rowTotal: 0,
      tableHeaderDefine: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          type: 'index',
          width: 50,
          align: 'center'
        }, {
          title: '标题',
          key: 'title'
        }, {
          title: '连载',
          key: 'seriesName'
        }, {
          title: '连载顺序',
          key: 'seriesOrder'
        }, {
          title: '浏览量',
          key: 'viewNum'
        }, {
          title: '更新时间',
          key: 'updateTime',
          render: (h, params) => {
            return h('span', getRelativeTime(params.row.updateTime, 'year'))
          }
        }, {
          title: '操作',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.handleViewArticle(params.row.id)
                  }
                }
              }, '预览'),
              h('Poptip', {
                props: {
                  confirm: true,
                  title: '确定要删除吗?'
                },
                on: {
                  'on-ok': () => {
                    this.handleSingleDelete(params.row.id)
                  },
                  'on-cancel': () => { this.handleCancelDelete() }
                }
              }, [
                h('Button', {
                  props: {
                    type: 'warning',
                    size: 'small'
                  }
                }, '删除')
              ])
            ])
          }
        }
      ],
      currentSelect: [],
      data: [],
      drawerVisiable: false,
      articleObj: {
        content: '',
        title: ''
      },
      isTableLoading: false
    }
  },
  computed: {
    canDoBatch () {
      return this.currentSelect.length > 0
    }
  },
  mounted () {
    this.handleArticleLst()
  },
  methods: {
    ...mapActions([
      'getArticleLst', 'deleteArticle', 'batchDeleteArticleLst', 'getArticle'
    ]),
    // 处理文章列表
    handleArticleLst () {
      this.isTableLoading = true
      this.getArticleLst(this.params).then(data => {
        this.isTableLoading = false
        if (data.lst) {
          this.data = data.lst
          this.rowTotal = data.total
        } else {
          this.data = []
        }
      }).catch(() => {
        this.isTableLoading = false
      })
    },
    // 翻页
    handlePageChange (val) {
      this.params.pageNum = val
      this.handleArticleLst()
    },
    // 选中项变化
    handleSelectionChange (sel) {
      this.currentSelect = sel
    },
    // 删除已选项目
    handleDeleteSelection () {
      this.$Modal.confirm({
        title: '提示',
        content: '确定删除已选项目吗?',
        onOk: () => {
          this.batchDeleteArticleLst(this.currentSelect).then(() => {
            this.handleArticleLst()
          }).catch(error => {
            console.debug(error.message)
          })
        }
      })
    },
    // 取消删除
    handleCancelDelete () {
      console.debug('do nothing...')
    },
    // 导出已选项目
    handleExportSelection () {
      if (this.currentSelect && this.currentSelect.length > 0) {
        let sel = ''
        this.currentSelect.map(val => {
          sel += val.id + ','
        })
        let baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro
        window.location.href = baseUrl + 'blog/article-files?idStr=' + sel.substring(0, sel.length - 1)
      } else {
        this.$Message.warning({
          content: '尚未选择项目'
        })
      }
    },
    // 删除单个项目
    handleSingleDelete (val) {
      this.deleteArticle(val).then(() => {
        // 删除成功后刷新文章列表
        this.handleArticleLst()
      }).catch(error => { console.debug(error.message) })
    },
    // 预览文章
    handleViewArticle (val) {
      this.getArticle(val).then(data => {
        this.articleObj = data
        this.drawerVisiable = true
      }).catch(error => {
        console.debug(error.message)
      })
    }
  }
}
</script>

<style lang="less">
.table-tools-wrap {
  padding: 0 10px;
  .item-margin {
    margin: 0 5px;
  }
}
</style>
