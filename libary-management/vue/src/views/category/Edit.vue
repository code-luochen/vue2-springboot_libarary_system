<template>
    <div class="box">
        <div class="adduser">编辑分类</div>
        <el-form :inline="true" class="form" :rules="rules" ref="rulesForm" :model="form" label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="update" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { getById, eidtCategory } from "@/api/requireCategory"
export default {
    name: 'EditCategory',
    data() {
        return {
            // 表单数据
            form: {
                name: '',
                remark: ''
            },
            // 用户id
            id: 0,
            // 表单验证
            rules: {
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ]

            }
        }
    },
    created() {
        this.id = this.$route.query.id
        this.queryById()
    },
    methods: {
        // 根据id查询数据
        async queryById() {
            const res = await getById(this.id)
            if (res.code !== "200") return this.$message.error("获取数据失败")
            this.form = res.data
        },

        // 提交更新数据
        async update() {

            this.$refs.rulesForm.validate(async (valid) => {
                if (valid) {
                    const res = await eidtCategory(this.form)
                    if (res.code !== "200") return this.$message.error("修改数据失败")
                    this.$message.success("修改成功")
                    this.$router.push("/category")
                } else {
                    this.$message.info("请按要求填写内容")
                    return false;
                }
            });


        },
        // 清空表单
        clean() {
            this.form = {}
        }

    }
}
</script>

<style lang="less" scoped>
.box {
    display: block;
    width: 80%;
}

.adduser {
    margin-bottom: 30px;
}

.btn {
    text-align: center;
    margin-top: 30px;
}
</style>