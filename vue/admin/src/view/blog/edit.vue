<template>
  <Row :gutter="16">
    <Col span="18">
      <Card shadow>
        <Form :model="articleForm" :label-width="80">
          <FormItem label="博文标题">
            <Input v-model="articleForm.title" placeholder="请输入文章标题" />
          </FormItem>
        </Form>
        <markdown-editor v-model="articleForm.content"/>
      </Card>
    </Col>
    <Col span="6">
      <Card shadow>
        <p slot="title">
          <Icon type="ios-navigate" /> 发布
        </p>
        <div class="release-btns">
            <Button type="primary" @click="release">发布</Button>
            <Button style="margin: 0 10px;">保存草稿</Button>
            <Button>预览</Button>
          </div>
      </Card>
      <Card shadow style="margin-top:16px;">
        <p slot="title">
          <Icon type="md-folder-open" /> 整理
        </p>
        <a href="#" slot="extra" @click.prevent="resetTagAndSeries">
            <Icon type="ios-loop-strong"></Icon> 重置
        </a>
        <Form :model="articleForm" :label-width="40">
          <FormItem label="标签">
            <Select v-model="selectedTagLst" multiple :max-tag-count="2">
              <Option v-for="item in tagLst" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
          <FormItem label="连载">
            <Select v-model="articleForm.series">
              <Option v-for="item in seriesLst" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Form>
      </Card>
      <Card shadow style="margin-top:16px;">
        <p slot="title">
          <Icon type="md-albums" /> 附加
        </p>
        <Form :model="articleForm" :label-width="40">
          <FormItem label="配图">
            <Input v-model="articleForm.abstractImg" placeholder="输入图片网址"/>
          </FormItem>
        </Form>
      </Card>
    </Col>
  </Row>
</template>

<script>
import MarkdownEditor from '_c/markdown'

import { getSeries } from '@/api/series'
import { getTags } from '@/api/tag'
import { postArticles } from '@/api/article'
export default {
  components: {
    MarkdownEditor
  },
  data () {
    return {
      articleForm: {
        title: '',
        content: '',
        tagList: [],
        series: '',
        abstractImg: ''
      },
      selectedTagLst: [],
      tagLst: [],
      seriesLst: []
    }
  },
  mounted () {
    this.getSeries()
    this.getTags()
  },
  methods: {
    release () { // 发表文章
      // 验证数据是否输入完整
      // if (this.articleForm.title.trim().length === 0) {
      //   this.$Message.error({
      //     content: '标题不能为空'
      //   })
      // } else
      if (this.articleForm.content.trim().length === 0) {
        this.$Message.error({
          content: '内容不能为空'
        })
      } else {
        this.articleForm.tagList = []
        this.selectedTagLst.map(val => {
          this.articleForm.tagList.push({ id: val })
        })
        postArticles(this.articleForm)
      }
    },
    getSeries () { // 获取连载列表
      getSeries().then(data => {
        // 整理返回结果
        let lst = []
        data.data.data.map(val => {
          lst.push({ label: val.name, value: val.id })
        })
        this.seriesLst = lst
      })
    },
    getTags () { // 获取标签列表
      getTags().then(data => {
        // 整理返回结果
        let lst = []
        data.data.data.map(val => {
          lst.push({ label: val.title, value: val.id })
        })
        this.tagLst = lst
      })
    },
    resetTagAndSeries () {
      this.articleForm.tagList = []
      this.articleForm.series = ''
    }
  }
}
</script>

<style lang="less" scoped>
@import './less/edit.less';
</style>
