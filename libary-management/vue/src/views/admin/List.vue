<template>
  <div>
    <!-- 搜索表单 -->
    <div class="search">
      <el-input placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-input placeholder="请输入邮箱" v-model="params.email"></el-input>
      <el-button type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="50" align="center">
      </el-table-column>
      <el-table-column prop="username" label="用户名" align="center">
      </el-table-column>
      <el-table-column prop="phone" label="电话" align="center">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" align="center">
      </el-table-column>
      <el-table-column prop="updatetime" label="更新时间" align="center">
      </el-table-column>
      <!-- 状态 -->
      <el-table-column label="状态" align="center">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" @change="changeStatus(scope.row)" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>

      <!-- 操作部分 -->
      <el-table-column label="操作" align="center" width="240">
        <template v-slot="scope">
          <!-- socpe.row就是当前行的数据 -->
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="open(scope.row)">删除</el-button>
          <el-button type="warning" @click="handlerPass(scope.row)">修改密码</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页模块 -->
    <el-pagination background layout="prev, pager, next" :total="total" :current-page="params.pageNum"
      :page-size="params.pageSize" @current-change="handleCurrentChange">
    </el-pagination>

    <!-- 修改密码dialog -->
    <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rulesForm">
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="form.newPass" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePass">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getList, deleteById, changePass,eidtAdmin } from '@/api/requireadmin'
import Cookies from 'js-cookie'
export default {
  name: 'Admin',
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
        username: '',
        phone: '',
        email: ''
      },
      form: {},
      // 打开dialog
      dialogVisible: false,
      rulesForm: {
        newPass: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在3-10个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    // 改变状态
    async changeStatus(row){

      // 我们应该保证有当前登录的管理员是不能够将自己禁用的
      if(this.admin.id==row.id && !row.status){
        row.status=true
        return this.$message.warning("您的操作不合法")
      }
      const res = await eidtAdmin(row)
      if(res.code!=='200') return this.$message.error("修改状态失败")
      this.$message.success("操作成功")
      this.load()
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
      this.$router.push("/editAdmin?id=" + row.id)
    },
    // 删除操作
    open(row) {
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
    // 修改密码
    handlerPass(row) {
      // 让原来的对象值，和修改的这个不关联
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    savePass() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          const res = await changePass(this.form)
          if (res.code !== '200') return this.$message.error(res.msg)
          this.$message.success('修改成功')
          // 如果当前修改的id等于登录的id那么，那么就要求用户重新登录
          if (this.form.id === this.admin.id) {
            Cookies.remove("admin")
            this.$router.push("/login")
          } else {
            this.load()
            this.dialogVisible = false

          }

        } else {
          this.$message.error(res.msg)
        }
      });
    }
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
</style>