<template>
    <div>
      <!-- 查询条件及操作 -->
      <Card>
        <Button type="primary" icon="md-add" shape="circle" class="margin-h" @click="addMock">新增</Button>
        <Button :disabled="!addMockForm.id" icon="md-create" shape="circle" class="margin-h" @click="editMock">编辑</Button>
        <Button :disabled="!addMockForm.id" type="error" icon="md-trash" shape="circle" class="margin-h" @click="handleDeleteMock">删除</Button>
      </Card>
      <!-- 数据展示 -->
      <Card class="margin-top">
        <Table :columns="mockColumns" :data="mockList" highlight-row @on-row-click="selectedRow"></Table>
        <Page :current.sync="page" :page-size="pageSize" :total="total" show-total class="margin-top" @on-change="handleMockList"></Page>
      </Card>
      <!-- 新增模拟数据对话框 -->
      <Modal v-model="addMockVisible" title="新增模拟数据">
        <Form ref="addMock" :label-width="100" :model="addMockForm" :rules="addMockRules">
          <FormItem label="所属转发服务">
            <Select v-model="addMockForm.dispatchId">
              <Option v-for="item in dispatchList" :key="item.id" :value="item.id">{{item.serviceName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="请求方法">
            <Select v-model="addMockForm.method">
              <Option v-for="(item, index) in methodList" :key="index" :value="item">{{item}}</Option>
            </Select>
          </FormItem>
          <FormItem label="请求路径" prop="path">
            <Input v-model="addMockForm.path" placeholder="请输入模拟数据的相对路径" />
          </FormItem>
          <FormItem label="备注">
            <Input v-model="addMockForm.remark" placeholder="请输入备注信息" />
          </FormItem>
          <FormItem label="响应体" prop="resbody">
            <Input v-model="addMockForm.resbody" :rows="4" type="textarea" placeholder="请输入响应体" />
          </FormItem>
        </Form>
        <div slot="footer">
          <Button :loading="addMockBtnLoading" type="primary" @click="handleAddMock">提交</Button>
        </div>
      </Modal>
    </div>
</template>

<script>

import { mapActions } from 'vuex'

const methods = ['GET', 'POST', 'PUT', 'DELETE']
const amkRules = {
  path: [
    { required: true, message: '路径不能为空', trigger: 'blur' }
  ],
  resbody: [
    { required: true, message: '响应体不能为空', trigger: 'blur' }
  ]
}
const mkColumns = [
  {
    type: 'index',
    width: '80',
    align: 'center'
  },
  {
    title: '匹配路径',
    key: 'path'
  },
  {
    title: '请求方法',
    key: 'method'
  },
  {
    title: '所属服务',
    key: 'dispatchName'
  },
  {
    title: '备注',
    key: 'remark'
  },
  {
    title: '响应内容',
    key: 'resbody',
    tooltip: true
  }
]
export default {
  name: 'mock-page',
  data () {
    return {
      optType: 'add',
      // 新增模拟数据相关变量
      addMockVisible: false,
      addMockBtnLoading: false,
      dispatchList: [],
      methodList: methods,
      addMockForm: {
        dispatchId: '',
        method: '',
        path: '',
        remark: '',
        resbody: ''
      },
      addMockRules: amkRules,
      // 模拟数据列表相关变量
      mockList: [],
      mockColumns: mkColumns,
      page: 1,
      pageSize: 20,
      total: 0
    }
  },
  mounted () {
    this.handleMockList()
    this.handleDispatches()
  },
  methods: {
    ...mapActions([
      'getMocks', 'postMocks', 'getDispatches', 'putMocks', 'deleteMocks'
    ]),
    // 获取模拟数据列表
    async handleMockList () {
      try {
        const param = {
          param: {
            pageNum: this.page,
            pageSize: this.pageSize
          }
        }
        const data = await this.getMocks(param)
        this.mockList = data.lst
        this.total = data.total
      } catch (e) {
        console.error(e)
        this.mockList = []
        this.total = 0
      }
    },
    // 新增或修改模拟数据
    async handleAddMock () {
      this.addMockBtnLoading = true
      try {
        const params = {
          data: this.addMockForm
        }
        if (this.addMockForm.id) {
          await this.putMocks(params)
        } else {
          await this.postMocks(params)
        }
        this.handleMockList()
      } catch (e) {
        console.error(e)
      } finally {
        this.addMockBtnLoading = false
        this.addMockVisible = false
      }
    },
    // 删除模拟数据
    async handleDeleteMock () {
      try {
        const params = {
          data: {
            id: this.addMockForm.id
          }
        }
        await this.deleteMocks(params)
        this.handleMockList()
      } catch (e) {
        console.error(e)
      }
    },
    // 获取转发列表
    async handleDispatches () {
      try {
        const data = await this.getDispatches({})
        this.dispatchList = data.lst
      } catch (e) {
        console.error(e)
      }
    },
    // 新增模拟数据
    addMock () {
      this.optType = 'add'
      Object.keys(this.addMockForm).forEach(k => { this.addMockForm[k] = '' })
      this.addMockVisible = true
    },
    // 编辑某条模拟数据
    editMock () {
      this.optType = 'edit'
      this.addMockVisible = true
    },
    // 点击某一条模拟数据
    selectedRow (row) {
      this.addMockForm = row
    }
  }
}
</script>
