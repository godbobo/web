<template>
  <div>
    <!-- 用户操作区域 -->
    <Card>
      <Button type="primary" icon="md-add" class="margin-h" shape="circle" @click="addUserModal">添加用户</Button>
      <Button :disabled="!this.userForm.id" type="primary" icon="md-create" class="margin-h" shape="circle" @click="editUserModal">编辑</Button>
      <Poptip confirm title="默认密码为123，确定要重置密码吗？" placement="bottom" @on-ok="resetUserPassword">
        <Button :disabled="!this.userForm.id" type="warning" icon="md-undo" class="margin-h" shape="circle">重置密码</Button>
      </Poptip>
      <Button :disabled="!this.userForm.id" type="primary" icon="md-key" class="margin-h" shape="circle" @click="handleRoleOfUser">角色设置</Button>
    </Card>
    <Card class="margin-top">
      <!-- 用户查询表单 -->
      <Form :label-width="60" inline>
        <FormItem label="邮箱">
          <Input v-model="userQueryForm.email" placeholder="请输入邮箱" />
        </FormItem>
        <FormItem label="用户名">
          <Input v-model="userQueryForm.username" placeholder="请输入用户名" />
        </FormItem>
        <FormItem label="姓名">
          <Input v-model="userQueryForm.realName" placeholder="请输入姓名" />
        </FormItem>
        <Button icon="md-search" type="primary" shape="circle" class="margin-h" @click="handleUsers">搜索</Button>
        <Button icon="md-undo" shape="circle" class="margin-h" @click="resetUserQueryForm">重置</Button>
      </Form>
      <!-- 用户表格区域 -->
      <Table :columns="userColumns" :data="userList" :loading="userTableLoading" highlight-row @on-row-click="rowSelected">
        <template slot="gender" slot-scope="{row}">
          <span>{{genderFormatter(row.gender)}}</span>
        </template>
        <template slot="status" slot-scope="{row}">
          <i-switch :value="row.enabled" size="large" @on-change="userStateChanged(row)">
            <span slot="open">有效</span>
            <span slot="close">无效</span>
          </i-switch>
        </template>
      </Table>
      <Page :current="pageNum" :page-size="pageSize" :total="total" show-total class="margin-top" @on-change="handleUsers"></Page>
    </Card>
    <!-- 用户表单对话框 -->
    <Modal v-model="userModalVisible" :title="userModalTitle">
      <Form ref="userForm" :model="userForm" :rules="userRules" :label-width="80">
        <FormItem label="邮箱" prop="email">
          <Input v-model="userForm.email" type="email" placeholder="请输入邮箱（作为登录名使用）" />
        </FormItem>
        <FormItem v-show="optType === 'add'" label="密码" prop="password">
          <Input v-model="userForm.password" type="password" placeholder="请输入密码" />
        </FormItem>
        <FormItem label="昵称" prop="username">
          <Input v-model="userForm.username" placeholder="请输入昵称" />
        </FormItem>
        <FormItem label="姓名">
          <Input v-model="userForm.realName" placeholder="请输入真实姓名" />
        </FormItem>
        <FormItem label="性别">
          <RadioGroup v-model="userForm.gender">
            <Radio label="0">保密</Radio>
            <Radio label="1">男</Radio>
            <Radio label="2">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="手机号">
          <Input v-model="userForm.tel" placeholder="请输入手机号" />
        </FormItem>
        <FormItem label="头像">
          <Input v-model="userForm.headImg" placeholder="请输入头像地址" />
        </FormItem>
        <FormItem label="签名">
          <Input v-model="userForm.sign" :rows="2" type="textarea" placeholder="请输入签名" />
        </FormItem>
      </Form>
      <template slot="footer">
        <Button :loading="userFormLoading" type="primary" shape="circle" @click="submitUserForm">提交</Button>
      </template>
    </Modal>
    <!-- 角色管理抽屉区域 -->
    <Drawer v-model="roleDrawerVisible" width="300" title="角色设置">
      <List>
        <ListItem v-for="(item, index) in allRoles" :key="index">
          <ListItemMeta :title="item.remark" />
          <template slot="action">
            <i-switch v-model="item.enable" @on-change="handleUpdateUserRoles(item.name)"></i-switch>
          </template>
        </ListItem>
        <!-- <template slot="footer">
          <Button icon="md-add" type="primary" shape="circle">添加角色</Button>
        </template> -->
      </List>
    </Drawer>
  </div>
</template>

<script>

import { mapActions } from 'vuex'

const uRules = {
  email: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '用户名不能为空', trigger: 'blur' }
  ]
}

const uColumns = [
  {
    type: 'index',
    width: 60,
    align: 'center'
  },
  {
    title: '昵称',
    key: 'username'
  },
  {
    title: '姓名',
    key: 'realName'
  },
  {
    title: '邮箱',
    key: 'email'
  },
  {
    title: '性别',
    slot: 'gender',
    width: 100,
    align: 'center'
  },
  {
    title: '手机号',
    key: 'tel'
  },
  {
    title: '状态',
    slot: 'status',
    width: 120,
    align: 'center'
  }
]

export default {
  name: 'user-list-page',
  data () {
    return {
      // 用户表单相关变量
      userModalVisible: false,
      optType: 'add',
      userForm: {
        email: '',
        gender: '0',
        headImg: '',
        password: '',
        realName: '',
        sign: '',
        tel: '',
        username: ''
      },
      userRules: uRules,
      userFormLoading: false,
      // 用户表格相关变量
      userColumns: uColumns,
      userList: [],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      userTableLoading: false,
      // 查询用户相关变量
      userQueryForm: {
        id: '',
        username: '',
        realName: '',
        email: ''
      },
      // 角色管理相关变量
      roleDrawerVisible: false,
      allRoles: []
    }
  },
  computed: {
    userModalTitle () {
      return this.optType === 'add' ? '添加用户' : '编辑用户'
    }
  },
  mounted () {
    this.handleUsers()
    this.handleAllRoles()
  },
  methods: {
    ...mapActions([
      'addUser', 'selectUser', 'editUser', 'getRoles', 'addRole', 'getRolesByUid', 'updateUserRoles'
    ]),
    // 添加或修改用户
    async handleAddOrEditUser () {
      this.userFormLoading = true
      try {
        if (this.userForm.id) {
          if (this.optType === 'edit') {
            this.userForm.password = ''
          }
          await this.editUser(this.userForm)
        } else {
          await this.addUser(this.userForm)
        }
      } catch (e) {
        console.error(e)
      } finally {
        this.userFormLoading = false
        this.userModalVisible = false
      }
    },
    // 查询用户
    async handleUsers () {
      this.userTableLoading = true
      try {
        this.userQueryForm.pageNum = this.pageNum
        this.userQueryForm.pageSize = this.pageSize
        const data = await this.selectUser(this.userQueryForm)
        this.userList = data.lst
        this.total = data.total
      } catch (e) {
        console.error(e)
        this.userList = []
      } finally {
        this.userTableLoading = false
      }
    },
    // 获取全部角色
    async handleAllRoles () {
      try {
        const data = await this.getRoles()
        this.allRoles = data.lst
      } catch (e) {
        console.error(e)
      }
    },
    /**
     * 获取用户的角色
     */
    async handleRoleOfUser () {
      this.roleDrawerVisible = true
      try {
        const param = {
          uid: this.userForm.id
        }
        const data = await this.getRolesByUid(param)
        this.allRoles.forEach(r => {
          r.enable = false
        })
        const tempRoles = this.allRoles
        this.allRoles = []
        // 将用户拥有的角色设置为开启状态
        data.lst.forEach(role => {
          tempRoles.some(r => {
            if (role.name === r.name) {
              r.enable = true
              return true
            }
          })
        })
        this.allRoles = tempRoles
      } catch (e) {
        console.error(e)
      }
    },
    /**
     * 修改用户角色
     */
    async handleUpdateUserRoles (roleName) {
      try {
        const param = {
          id: this.userForm.id,
          roleName: roleName
        }
        await this.updateUserRoles(param)
      } catch (e) {
        console.error(e)
      }
    },
    // 添加用户
    addUserModal () {
      this.optType = 'add'
      Object.keys(this.userForm).forEach(k => {
        this.userForm[k] = ''
      })
      this.userModalVisible = true
    },
    // 编辑用户
    editUserModal () {
      this.optType = 'edit'
      // 填充密码，避免表单验证异常
      this.userForm.password = '123'
      this.userModalVisible = true
    },
    // 重置密码
    resetUserPassword () {
      this.optType = 'resetPwd'
      this.userForm.password = '123'
      this.handleAddOrEditUser()
    },
    // 提交用户表单
    submitUserForm () {
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          this.handleAddOrEditUser()
        }
      })
    },
    // 格式化性别
    genderFormatter (g) {
      switch (g) {
        case 1: return '男'
        case 2: return '女'
        default: return '保密'
      }
    },
    // 重置用户查询表单
    resetUserQueryForm () {
      Object.keys(this.userQueryForm).forEach(k => {
        this.userQueryForm[k] = ''
      })
      this.pageNum = 1
      this.handleUsers()
    },
    // 选中某一行
    rowSelected (row) {
      this.userForm = row
    },
    /**
     * 切换用户状态
     */
    userStateChanged (row) {
      this.userForm = JSON.parse(JSON.stringify(row))
      this.userForm.enabled = this.userForm.enabled ? 0 : 1
      this.optType = 'edit'
      this.handleAddOrEditUser()
    }
  }
}
</script>
