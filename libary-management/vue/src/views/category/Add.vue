<template>
    <div class="box">
        <div class="adduser">新增分类</div>
        <el-form :inline="true" class="form" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="备注">
                <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="save" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { saveCategory } from "@/api/requireCategory"
export default {
    name: 'AddCategory',
    data() {
       
        return {
            // 表单数据
            form: {
                name: '',
                remark: ''
            },
            // 表单验证
            rules: {
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ]

            }
        }
    },
    methods: {
        // 添加新用户
        save() {
            this.$refs.ruleForm.validate(async (valid) => {
                if (valid) {
                    const res = await saveCategory(this.form)
                    if (res.code !== "200") return this.$message.error(res.msg)
                    this.$refs.ruleForm.resetFields();
                    this.$message.success("添加成功")
                } else {
                    this.$message.info("请按要求填写内容")
                    return false;
                }
            });

        },
        
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