<template>
  <div>
    <Card>
      <Button type="primary" icon="md-add" shape="circle" @click="addUserModal">添加用户</Button>
    </Card>
    <!-- 用户表单对话框 -->
    <Modal v-model="userModalVisible" :title="userModalTitle">
      <Form ref="userForm" :model="userForm" :rules="userRules" :label-width="80">
        <FormItem label="邮箱" prop="email">
          <Input v-model="userForm.email" type="email" placeholder="请输入邮箱（作为登录名使用）" />
        </FormItem>
        <FormItem label="密码" prop="password">
          <Input v-model="userForm.password" type="password" placeholder="请输入密码" />
        </FormItem>
        <FormItem label="姓名">
          <Input v-model="userForm.realName" placeholder="请输入真实姓名" />
        </FormItem>
        <FormItem label="昵称" prop="username">
          <Input v-model="userForm.username" placeholder="请输入昵称" />
        </FormItem>
        <FormItem label="签名">
          <Input v-model="userForm.sign" :rows="2" type="textarea" placeholder="请输入签名" />
        </FormItem>
        <FormItem label="头像">
          <Input v-model="userForm.headImg" placeholder="请输入头像地址" />
        </FormItem>
        <FormItem label="手机号">
          <Input v-model="userForm.tel" placeholder="请输入手机号" />
        </FormItem>
        <FormItem label="性别">
          <Input v-model="userForm.gender" placeholder="请输入性别" />
        </FormItem>
      </Form>
      <template slot="footer">
        <Button :loading="userFormLoading" type="primary" shape="circle" @click="submitUserForm">提交</Button>
      </template>
    </Modal>
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

export default {
  name: 'user-list-page',
  data () {
    return {
      // 用户表单相关变量
      userModalVisible: false,
      optType: 'add',
      userForm: {
        email: '',
        gender: '',
        headImg: '',
        password: '',
        realName: '',
        sign: '',
        tel: '',
        username: ''
      },
      userRules: uRules,
      userFormLoading: false
    }
  },
  computed: {
    userModalTitle () {
      return this.optType === 'add' ? '添加用户' : '编辑用户'
    }
  },
  mounted () {

  },
  methods: {
    ...mapActions([
      'addUser'
    ]),
    // 添加或修改用户
    async handleAddOrEditUser () {
      this.userFormLoading = true
      try {
        const data = await this.addUser(this.userForm)
        console.log(data)
      } catch (e) {
        console.error(e)
      } finally {
        this.userFormLoading = false
        this.userModalVisible = false
      }
    },
    // 添加用户
    addUserModal () {
      this.optType = 'add'
      this.userModalVisible = true
    },
    // 提交用户表单
    submitUserForm () {
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          this.handleAddOrEditUser()
        }
      })
    }
  }
}
</script>
