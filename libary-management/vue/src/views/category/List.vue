<template>
  <div>
    <!-- 搜索表单 -->
    <div class="search">
      <el-input placeholder="请输入分类名称" v-model="params.name"></el-input>
      <el-button type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" label="编号" width="60" align="center">
      </el-table-column>
      <el-table-column prop="name" label="名称" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" align="center">
      </el-table-column>
      <el-table-column prop="updatetime" label="更新时间" align="center">
      </el-table-column>


      <!-- 操作部分 -->
      <el-table-column label="操作" align="center" width="270">
        <template v-slot="scope">
          <!-- socpe.row就是当前行的数据 -->
          <el-button type="success" v-if="!scope.row.pid" @click="handleAdd(scope.row)">添加二级分类</el-button>
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="del(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页模块 -->
    <el-pagination background layout="prev, pager, next" :total="total" :current-page="params.pageNum"
      :page-size="params.pageSize" @current-change="handleCurrentChange">
    </el-pagination>

    <!-- 修改密码dialog -->
    <el-dialog title="添加二级分类" :visible.sync="dialogVisible" width="30%">
      <el-form class="dialogAdd" :model="form" label-width="100px" ref="formRef" :rules="rulesForm">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="分类备注">
          <el-input v-model="form.remark" autocomplete="off" ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
import { getList, deleteById, saveCategory } from '@/api/requireCategory'
import Cookies from 'js-cookie'
export default {
  name: 'Category',
  data() {
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
      // 存放读者的数组
      tableData: [],
      dialogVisible: false,
      // 总的读者数
      total: 0,
      // 条件查询参数
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
      },
      form: {},
      pid: null,
      rulesForm: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ]

      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleAdd(row) {
      // 将当前行的id作为加入二级分类的pid
      this.pid = row.id
      this.dialogVisible = true
    },
    // 保存分类
    save() {
      this.form.pid=this.pid
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          const res = await saveCategory(this.form)
          if (res.code !== "200") return this.$message.error(res.msg)
          this.$refs.formRef.resetFields();
          this.form.remark=''
          this.dialogVisible=false
          this.load()
          this.$message.success("添加二级分类成功")
        } else {
          this.$message.info("请按要求填写内容")
          return false;
        }
      });
    },

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
      this.$router.push("/editCategory?id=" + row.id)
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