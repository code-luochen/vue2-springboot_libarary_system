<template>
  <div>
    <!-- 搜索表单 -->
    <div class="search">
      <el-input placeholder="请输入名称" v-model="params.name"></el-input>
      <el-input placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="50" align="center">
      </el-table-column>
      <el-table-column prop="username" label="会员号" width="80" align="center">
      </el-table-column>
      <el-table-column prop="name" label="名称" align="center">
      </el-table-column>
      <el-table-column prop="age" label="年龄" align="center">
      </el-table-column>
      <el-table-column prop="address" label="地址" align="center">
      </el-table-column>
      <el-table-column prop="phone" label="电话" align="center">
      </el-table-column>
      <el-table-column prop="sex" label="性别" align="center">
      </el-table-column>
      <el-table-column prop="account" label="账户积分" align="center">
      </el-table-column>
      <!-- 状态 -->
      <el-table-column label="状态" align="center">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" @change="changeStatus(scope.row)" active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" align="center">
      </el-table-column>
      <el-table-column prop="updatetime" label="更新时间" align="center">
      </el-table-column>


      <!-- 操作部分 -->
      <el-table-column label="操作" align="center" width="220">
        <template v-slot="scope">
          <el-button type="warning" @click="handleAccountAdd(scope.row)">充值</el-button>
          <!-- socpe.row就是当前行的数据 -->
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="open(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="充值" :visible.sync="dialogVisible" width="30%">
      <el-form class="dialogAdd" :model="form" label-width="100px" ref="formRef" :rules="rulesForm">

        <el-form-item label="当前账户积分" prop="account">
          <el-input v-model="form.account" disabled autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="积分" prop="score">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAccuntMoney">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 分页模块 -->
    <el-pagination background layout="prev, pager, next" :total="total" :current-page="params.pageNum"
      :page-size="params.pageSize" @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
import { getList, deleteById, addAccount,eidtUser } from '@/api/require'
export default {
  name: 'User',
  data() {
    const checkNums = (rule, value, callback) => {
      value = parseInt(value)
      if (value < 10 || value > 200) {
        return callback(new Error('请输入大于等于10小于等于200的整数'));
      }
      // 输入成功后也有一个回调函数
      callback()
    };
    return {
      // 存放读者的数组
      tableData: [],
      // 总的读者数
      total: 0,
      // 条件查询参数
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      },
      form: {},
      dialogVisible: false,
      rulesForm: {
        score: [
          { required: true, message: '请输入积分', trigger: 'blur' },
          { validator: checkNums, trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    // 改变状态
    async changeStatus(row) {
      const res = await eidtUser(row)
      if (res.code !== '200') return this.$message.error("修改状态失败")
      this.$message.success("操作成功")
      this.load()
    },
    // 获取读者列表
    async load() {
      // js内部自带的网络请求工具
      // fetch("http://localhost:9090/user/list").then(res=>res.json()).then(res=>{
      //   console.log(res);
      //   this.tableData=res
      // })
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
      this.$router.push("/edituser?id=" + row.id)
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
    // 打卡充值
    handleAccountAdd(row) {
      this.dialogVisible = true
      this.form = JSON.parse(JSON.stringify(row))
    },
    //  充值账户余额
    addAccuntMoney() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          const res = await addAccount(this.form)
          if (res.code !== "200") return this.$message.error(res.msg)
          this.$refs.formRef.resetFields();
          this.load()
          this.dialogVisible = false
          this.$message.success("充值成功")
        } else {
          this.$message.info("请按要求填写内容")
          return false;
        }
      });
    }
  }
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