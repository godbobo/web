<template>
  <Card shadow class="table-wrap">
    <Table highlight-row :columns="tableHeaderDefine" :data="data">
      <Page slot="footer" :total="100" show-elevator show-total />
    </Table>
  </Card>
</template>

<script>
import { mapActions } from 'vuex'
import { getRelativeTime } from '@/libs/tools'

export default {
  name: 'BlogArticlePage',
  data () {
    return {
      params: {
        title: '',
        pageNum: 1,
        pageSize: 10
      },
      tableHeaderDefine: [
        {
          type: 'index',
          width: 60,
          align: 'center'
        }, {
          title: '标题',
          key: 'title'
        }, {
          title: '连载',
          key: 'series'
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
          render: (h) => {
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
                    this.show(params.index)
                  }
                }
              }, '预览'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.remove(params.index)
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      data: []
    }
  },
  mounted () {
    this.handleArticleLst()
  },
  methods: {
    ...mapActions([
      'getArticleLst'
    ]),
    // 处理文章列表
    handleArticleLst () {
      this.getArticleLst(this.params).then(data => {
        // let fLst = []
        if (data.lst) {
          // 将结果中的时间戳转为字符串
          // data.lst.map(val => {
          //   val[updateTime] = getDate(val.updateTime)
          //   fLst.push(val)
          // })
          this.data = data.lst
        } else {
          this.data = []
        }
      })
    }
  }
}
</script>

<style lang="less">

</style>
