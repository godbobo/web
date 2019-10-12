/* eslint-disable semi */
<template>
  <div>
    <Card shadow>
      <Button type="primary" class="margin-h" shape="circle" icon="md-add" @click="add">新增</Button>
      <Button
        :disabled="!selectedService.id"
        class="margin-h"
        shape="circle"
        icon="md-copy"
        @click="duplicate"
      >复制</Button>
      <Button
        :disabled="!selectedService.id"
        type="error"
        class="margin-h"
        shape="circle"
        icon="md-trash"
        @click="showConfirm"
      >删除</Button>
    </Card>
    <Card shadow class="margin-top">
      <Table
        stripe
        highlight-row
        :loading="loadingTable"
        :columns="columnDefine"
        :data="dispatches"
        @on-row-click="serviceSelected"
      >
        <!-- 状态slot -->
        <template slot-scope="{row}" slot="status">
          <div style="width: 100%;text-align:center;">
            <Icon v-if="row.enable === 1" type="md-checkmark" color="blue" />
            <Button v-if="row.enable !== 1" shape="circle" @click="handleEnableService(row)">启用</Button>
          </div>
        </template>
        <!-- 操作slot -->
        <template slot="options">
          <Button shape="circle" @click="showDrawer">
            <Icons type="edit"></Icons>编辑
          </Button>
        </template>
      </Table>
      <Page
        class="margin-top"
        :total="total"
        :current="page"
        :page-size="pageSize"
        size="small"
        show-total
        @on-change="pageChanged"
      />
    </Card>
    <!-- 抽屉 - 转发服务编辑 -->
    <Drawer title="编辑" width="720" :closable="false" v-model="drawerVisible">
      <Form ref="serviceForm" :model="selectedService" :rules="formRules">
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="转发服务名" prop="serviceName" label-position="top">
              <Input v-model="selectedService.serviceName" placeholder="请输入服务名称" />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="转发地址" prop="serviceUrl" label-position="top">
              <Input v-model="selectedService.serviceUrl" placeholder="请输入服务地址">
                <span slot="prepend">http://</span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="请求体前缀" label-position="top">
              <Input v-model="selectedService.reqPrefix" placeholder="请输入请求体前缀" />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="子请求体键名" label-position="top">
              <Input v-model="selectedService.reqTargetParam" placeholder="请输入子请求体" />
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="响应体格式" label-position="top">
              <Input v-model="selectedService.resBody" placeholder="请输入响应体格式" />
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="响应体数据键名" label-position="top">
              <Input v-model="selectedService.resDataKey" placeholder="请输入子响应体" />
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button type="primary" shape="circle" @click="validateOnSave">保存</Button>
      </div>
    </Drawer>
  </div>
</template>

<script>
import Icons from '@/components/icons'

import { mapActions } from 'vuex'

const colDefine = [
  {
    title: '序号',
    type: 'index',
    width: 80
  },
  {
    title: '服务名称',
    key: 'serviceName'
  },
  {
    title: '转发地址',
    key: 'serviceUrl'
  },
  {
    title: '请求体前缀',
    key: 'reqPrefix'
  },
  {
    title: '子请求体',
    key: 'reqTargetParam'
  },
  {
    title: '响应体',
    key: 'resBody'
  },
  {
    title: '键名',
    key: 'resDataKey',
    width: 80
  },
  {
    title: '状态',
    slot: 'status',
    align: 'center'
  },
  {
    title: '操作',
    slot: 'options',
    align: 'center'
  }
]

const serviceRules = {
  serviceName: [
    { required: true, message: '转发服务名不能为空', trigger: 'blur' }
  ],
  serviceUrl: [
    { required: true, message: '转发地址不能为空', trigger: 'blur' }
  ]
}

export default {
  name: 'dispatchList',
  components: {
    Icons
  },
  data () {
    return {
      dispatches: [],
      columnDefine: colDefine,
      loadingTable: false,
      page: 1,
      pageSize: 20,
      total: 0,
      selectedService: {},
      drawerVisible: false,
      formRules: serviceRules,
      serviceSaveLoading: false,
      enableBtnLoading: false
    }
  },
  mounted () {
    this.handleDispatches()
  },
  methods: {
    ...mapActions(['getDispatches', 'postDispatches', 'putDispatches', 'deleteDispatches', 'enableDispatchService']),
    // 获取转发服务列表
    async handleDispatches () {
      this.loadingTable = true
      try {
        const params = {
          param: {
            pageNum: this.page,
            pageSize: this.pageSize
          }
        }
        const data = await this.getDispatches(params)
        this.dispatches = data.lst
        this.total = data.total
      } catch (e) {
        console.error(e)
      } finally {
        this.loadingTable = false
      }
    },
    // 新增或修改转发服务
    async handleSaveService () {
      this.serviceSaveLoading = true
      try {
        const params = {
          data: this.selectedService
        }
        if (this.selectedService.id) {
          // 修改
          await this.putDispatches(params)
          this.handleDispatches()
        } else {
          // 新增
          await this.postDispatches(params)
          this.handleDispatches()
        }
      } catch (e) {
        console.error(e)
      } finally {
        this.serviceSaveLoading = false
        this.hideDrawer()
      }
    },
    // 开启转发服务
    async handleEnableService (row) {
      this.enableBtnLoading = true
      try {
        await this.enableDispatchService(row.id)
        this.handleDispatches()
      } catch (e) {
        console.error(e)
        this.$Message.error(e.message)
      } finally {
        this.enableBtnLoading = false
      }
    },
    // 删除转发服务
    async handleDeleteService () {
      try {
        const params = {
          data: {
            id: this.selectedService.id
          }
        }
        await this.deleteDispatches(params)
        this.handleDispatches()
      } catch (e) {
        console.error(e)
        this.$Message.error(e.message)
      }
    },
    pageChanged (e) {
      this.page = e
      this.handleDispatches()
    },
    serviceSelected (e) {
      this.selectedService = e
    },
    showDrawer () {
      this.drawerVisible = true
    },
    hideDrawer () {
      this.drawerVisible = false
    },
    duplicate () {
      // 删除id表示新增服务
      this.selectedService.id = ''
      delete this.selectedService.enable
      this.showDrawer()
    },
    add () {
      this.selectedService = {}
      this.showDrawer()
    },
    validateOnSave () {
      this.$refs['serviceForm'].validate((valid) => {
        if (valid) {
          this.handleSaveService()
        } else {
          this.$Message.error('表单信息不完整！')
        }
      })
    },
    showConfirm () {
      this.$Modal.confirm({
        title: '警告',
        content: this.selectedService.enable === 1 ? '该转发服务正在使用，确定要删除吗？' : `确认要删除转发服务【${this.selectedService.serviceName}】吗？`,
        onOk: () => {
          this.handleDeleteService()
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.demo-drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: center;
  background: #fff;
}
</style>
