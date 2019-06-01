<template>
  <div class="markdown-wrapper">
    {{ content }}
  </div>
</template>

<script>
export default {
  name: 'MdViewer',
  props: {
    content: {
      type: String,
      default: ''
    },
    options: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {}
  },
  methods: {
  },
  mounted () {
  }
}
</script>

<style lang="less">
.markdown-wrapper {
  padding: 15px 20px 0 15px;
}
</style>
