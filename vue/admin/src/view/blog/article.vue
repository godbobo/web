<template>
  <Card shadow class="table-wrap">
    <Table highlight-row :columns="columns1" :data="data1"></Table>
  </Card>
</template>

<script>
export default {
  data () {
    return {
      columns1: [
        {
          type: 'index',
          width: 60,
          align: 'center'
        },
        {
          title: '标题',
          key: 'name'
        },
        {
          title: '标签',
          key: 'age'
        },
        {
          title: '浏览量',
          key: 'address'
        }
      ],
      data1: [
        {
          name: 'John Brown',
          age: 18,
          address: 'New York No. 1 Lake Park',
          date: '2016-10-03'
        },
        {
          name: 'Jim Green',
          age: 24,
          address: 'London No. 1 Lake Park',
          date: '2016-10-01'
        },
        {
          name: 'Joe Black',
          age: 30,
          address: 'Sydney No. 1 Lake Park',
          date: '2016-10-02'
        },
        {
          name: 'Jon Snow',
          age: 26,
          address: 'Ottawa No. 2 Lake Park',
          date: '2016-10-04'
        }
      ]
    }
  }
}
</script>

<style lang="less">

</style>
