<template>
  <Row :gutter="10">
    <Col span="18">
      <Card shadow class="series-wrap">
        <p slot="title"><Icon type="logo-buffer"/> 连载列表</p>
        <Table highlight-row :columns="tableHeaderDefine" :loading="isTableLoading" :data="data" @on-selection-change="handleSelectionChange">
          <div slot="opts" slot-scope="{ row }">
            <Dropdown trigger="click" @on-click="handleOptClick">
              <Button size="small" @click="handleOptBtnClick(row.id)">操作</Button>
              <DropdownMenu slot="list">
                <DropdownItem name="addArticle">添加文章</DropdownItem>
                <DropdownItem name="chooseImg">修改配图</DropdownItem>
              </DropdownMenu>
            </Dropdown>
            <Poptip confirm title="确定要删除吗？" placement="left" style="margin-left: 5px;" @on-ok="handleSingleDelete">
              <Button type="warning" size="small">删除</Button>
            </Poptip>
          </div>
          <div slot="header" class="table-tools">
            <Button type="warning" :disabled="!canDoBatch" icon="md-trash" @click="handleDeleteSelection">批量删除</Button>
            <Button icon="md-add" style="margin-left:5px;" @click="addSeriesDialogVisiable = !addArticleDialogVisiable">新增连载</Button>
          </div>
        </Table>
        <Modal v-model="chooseImgDialogVisiable" width="50" title="配图选择">
          <cropper crop-button-text="确认提交" />
        </Modal>
        <Modal v-model="addArticleDialogVisiable" title="选择文章">
          <div style="height: 380px; overflow: auto; margin-bottom:10px;">
            <Alert>点击表格中的某一行即可选择</Alert>
            <Table highlight-row :columns="articleColumnDefine" :data="noHomeArticleLst" @on-current-change="handleSubmitRelateArticle">
            </Table>
          </div>
        </Modal>
        <Modal v-model="addSeriesDialogVisiable" :loading="dialogConfirmLoading" title="新增连载" @on-ok="handleSubmitSeries">
          <Form ref="seriesForm" :model="seriesForm" :rules="seriesRules">
            <FormItem label="连载名" prop="name">
              <Input v-model="seriesForm.name" placeholder="请输入连载名" />
            </FormItem>
            <FormItem label="连载简介" prop="abs">
              <Input v-model="seriesForm.abs" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入连载介绍" />
            </FormItem>
          </Form>
        </Modal>
      </Card>
    </Col>
    <Col span="6">
      <Affix :offset-top="130">
        <Card shadow class="article-wrap">
          <p slot="title"><Icon type="md-book"/> 文章列表</p>
          <div  style="max-height: 400px; overflow: auto;">
            <draggable v-model="articleLst" @start="handleDragStart" @end="handleDragEnd">
            <transition-group>
              <div v-for="(item) in articleLst" :key="item" class="article-item">
                <div class="article-title">{{item}}</div>
                <div class="article-btns">
                  <Button size="small" type="warning" ghost><Icon type="md-remove" /></Button>
                </div>
              </div>
            </transition-group>
          </draggable>
          </div>
        </Card>
      </Affix>
    </Col>
  </Row>
</template>

<script>
import draggable from 'vuedraggable'
import Cropper from '@/components/cropper'
import { getRelativeTime } from '@/libs/tools'
import { mapActions } from 'vuex'
export default {
  name: 'BlogSeries',
  components: {
    draggable, Cropper
  },
  data () {
    return {
      tableHeaderDefine: [
        {
          type: 'selection',
          width: 50,
          align: 'center'
        },
        {
          type: 'index',
          width: 50,
          align: 'center'
        }, {
          title: '连载名',
          key: 'name'
        }, {
          title: '简介',
          render: (h, params) => {
            return h('Tooltip', {
              props: {
                'max-width': '200',
                content: params.row.abs
              }
            }, params.row.abs.substring(0, 8))
          }
        }, {
          title: '文章数量',
          key: 'articleNum',
          align: 'center'
        }, {
          title: '添加章节索引',
          align: 'center',
          render: (h, params) => {
            return h('i-switch', {
              props: {
                value: params.row.autoIndex === 1
              },
              on: {
                'on-change': val => {
                  this.handleAutoIndexSwitchChange(params.index, params.row, val)
                }
              }
            }, [])
          }
        }, {
          title: '按索引排序',
          align: 'center',
          render: (h, params) => {
            return h('i-switch', {
              props: {
                value: params.row.ignoreOrder === 1
              },
              on: {
                'on-change': val => {
                  this.handleIndexOrderSwitchChange(params.index, params.row, val)
                }
              }
            }, [])
          }
        }, {
          title: '操作',
          slot: 'opts'
        }
      ],
      isTableLoading: false,
      data: [],
      currentSelection: [],
      articleLst: [],
      chooseImgDialogVisiable: false,
      addArticleDialogVisiable: false,
      articleColumnDefine: [
        {
          type: 'index',
          width: 50,
          align: 'center'
        }, {
          title: '标题',
          key: 'title'
        }, {
          title: '更新时间',
          render: (h, params) => {
            return h('span', getRelativeTime(params.row.updateTime, 'year'))
          }
        }
      ],
      noHomeArticleLst: [],
      addSeriesDialogVisiable: false,
      seriesForm: {
        name: '',
        abs: ''
      },
      seriesRules: {
        name: [{ required: true, message: '连载名必须填写', trigger: 'blur' }],
        abs: [{ required: true, message: '简介必须填写', trigger: 'blur' }]
      },
      dialogConfirmLoading: true,
      currentOptId: undefined
    }
  },
  computed: {
    canDoBatch () {
      return this.currentSelection.length > 0
    }
  },
  mounted () {
    this.handleSeriesLst()
    for (let i = 0; i < 12; i++) {
      this.articleLst.push('测试文章' + i)
    }
  },
  methods: {
    ...mapActions([
      'getSeriesLst', 'addSeries', 'updateSeries', 'getArticleLst', 'updateArticleSeriesMap'
    ]),
    // 获取连载文章
    handleSeriesLst () {
      this.isTableLoading = true
      this.getSeriesLst().then(data => {
        this.isTableLoading = false
        if (data) {
          this.data = data
        } else {
          this.data = []
        }
      }).catch(error => {
        this.isTableLoading = false
        console.debug(error.message)
      })
    },
    // 接收已选项目的变化
    handleSelectionChange (sel) {
      this.currentSelection = sel
    },
    // 接收自动索引开关的变化
    handleAutoIndexSwitchChange (index, row, val) {
      row.autoIndex = val ? 1 : 0
      // 服务端实体类数值字段设置了默认值的原因，不能单独提交一个字段的更新，干脆省点事，全部更新了
      this.updateSeries(row).then(data => {
        this.data[index] = row
      }).catch(error => {
        console.debug(error.message)
      })
    },
    // 接收按索引排序开关的变化
    handleIndexOrderSwitchChange (index, row, val) {
      row.ignoreOrder = val ? 1 : 0
      // 服务端实体类数值字段设置了默认值的原因，不能单独提交一个字段的更新，干脆省点事，全部更新了
      this.updateSeries(row).then(data => {
        this.data[index] = row
      }).catch(error => {
        console.debug(error.message)
      })
    },
    // 删除单个项目
    handleSingleDelete (id) {
      console.log(id)
    },
    // 删除所选项目
    handleDeleteSelection () {
      this.$Modal.confirm({
        title: '提示',
        content: '确定删除已选项目吗?',
        onOk: () => {

        }
      })
    },
    // 拖拽相关事件
    handleDragStart () {
      console.log('start')
    },
    handleDragEnd () {
      console.log('end')
    },
    // 点击操作栏中的下拉菜单
    handleOptClick (name) {
      if (name === 'addArticle') {
        this.addArticleDialogVisiable = true
        this.handleNoSeriesArticle()
      } else if (name === 'chooseImg') {
        this.chooseImgDialogVisiable = true
      }
    },
    // 点击操作按钮（确定点击的下拉菜单针对的是哪一行数据）
    handleOptBtnClick (id) {
      this.currentOptId = id
    },
    // 点击添加连载对话框中的确认按钮
    handleSubmitSeries () {
      this.$refs['seriesForm'].validate((valid) => {
        if (valid) {
          this.addSeries(this.seriesForm).then(data => {
            this.seriesForm.name = ''
            this.seriesForm.abs = ''
            this.addSeriesDialogVisiable = false
            this.handleSeriesLst()
          }).catch(error => {
            console.debug(error.message)
            this.addSeriesDialogVisiable = false
          })
        } else {
          this.addSeriesDialogVisiable = false
          this.$Message.error('请解决表单错误后再提交')
        }
      })
    },
    // 获取不在连载中的文章
    handleNoSeriesArticle () {
      this.getArticleLst({ queryTypes: 'noSeries' }).then(data => {
        this.noHomeArticleLst = data
      }).catch(error => {
        console.debug(error.message)
      })
    },
    // 点击提交文章连载
    handleSubmitRelateArticle (currentRow) {
      this.updateArticleSeriesMap({ aid: currentRow.id, sid: this.currentOptId }).then(() => {
        // 关闭对话框
        this.addArticleDialogVisiable = false
        this.handleSeriesLst()
      }).catch(error => {
        console.debug(error.message)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.table-tools {
  padding: 0 10px;
}

.article-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 2px 0;
  .article-title {
    overflow: hidden;
    flex-grow: 1;
    font-size: 14px;
    white-space: nowrap;
    margin-right: 10px;
  }
  &:hover {
    background: rgb(235, 247, 255);
  }
}
</style>
