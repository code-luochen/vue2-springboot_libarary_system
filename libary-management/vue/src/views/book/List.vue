<template>
  <div>
    <!-- 搜索表单 -->
    <div class="search">
      <el-input placeholder="请输入图书名称" v-model="params.name"></el-input>
      <el-input placeholder="请输入图书标准码" v-model="params.bookNo"></el-input>
      <el-button type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableData" stripe >
      <el-table-column prop="id" label="编号" width="60" align="center"></el-table-column>
     
      <el-table-column prop="name" label="图书名称"  align="center"></el-table-column>
      <el-table-column prop="bookNo" label="标准码" align="center"></el-table-column>
      <el-table-column prop="description" label="描述"  align="center"></el-table-column>
      <el-table-column prop="publishDate" label="发行日期" align="center"></el-table-column>
      <el-table-column prop="author" label="作者" align="center"></el-table-column>
      <el-table-column prop="publisher" label="出版社" align="center"></el-table-column>
      <el-table-column prop="category" label="分类" align="center"></el-table-column>
      <el-table-column prop="score" label="借书积分" align="center"></el-table-column>
      <el-table-column prop="nums" label="数量" align="center"></el-table-column>
      <el-table-column prop="cover" label="封面" align="center">
        <template v-slot="scope">
            <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" align="center"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间" align="center"></el-table-column>

      <!-- 操作部分 -->
      <el-table-column label="操作" align="center" width="180">
        <template v-slot="scope">
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页模块 -->
    <el-pagination background layout="prev, pager, next" :total="total" :current-page="params.pageNum"
      :page-size="params.pageSize" @current-change="handleCurrentChange">
    </el-pagination>

  </div>
</template>

<script>
import { getList, deleteById } from '@/api/requirebook'
import Cookies from 'js-cookie'
export default {
  name: 'Book',
  data() {
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
      // 存放读者的数组
      tableData: [],
      // 总的读者数
      total: 0,
      // 条件查询参数
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookNo:''
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    // 获取读者列表
    async load() {
      const res = await getList(this.params)
      if (res.code !== '200') return this.$message.error("获取数据失败")
      this.tableData = res.data.list
      this.total = res.data.total
    },
    // 重置
    reset() {
      this.params = {}
      this.load()
    },
    // 改变页数时的处理函数
    handleCurrentChange(pagenum) {
      this.params.pageNum = pagenum;
      this.load()
    },
    // 编辑用户
    edit(row) {
      this.$router.push("/editBook?id=" + row.id)
    },
    // 删除操作
    del(row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const res = await deleteById(row.id)
        if (res.code !== "200") return this.$message.error("删除失败")
        this.load()

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

  },

}

</script>

<style lang="less" scoped>
.search {
  .el-input {
    width: 240px;
    margin-right: 10px;
  }

}

.dialogAdd {
  width:85%;
}
</style>