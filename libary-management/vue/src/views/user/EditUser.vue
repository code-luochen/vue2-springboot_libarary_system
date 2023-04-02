<template>
    <div class="box">
        <div class="adduser">编辑用户</div>
        <el-form :inline="true" class="form" :rules="rulesForm" ref="ruleForm" :model="form" label-width="100px">
            <el-form-item label="卡号">
                <el-input v-model="form.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
                <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="form.address" placeholder="请输入地址"></el-input>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="update" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { getById, eidtUser } from "@/api/require"
export default {
    name: 'EditUser',
    data() {
        // 年龄自定义校验规则
        const checkAge = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('年龄不能为空'));
            }

            if (!/^\d[0-9]+$/.test(value)) {
                callback(new Error('请输入数字值'));
            }
            if (parseInt(value) > 120 || parseInt(value) < 0) {
                callback(new Error('请输入合理的年龄'))
            }
            // 输入成功后也有一个回调函数
            callback()
        };
        // 手机号码自定义校检规则
        const checkPhone = (rule, value, callback) => {
            if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
                return callback(new Error('请输入合法的电话号码'));
            }
            // 输入成功后也有一个回调函数
            callback()
        };
        return {
            // 表单数据
            form: {
                name: '',
                age: '',
                sex: '',
                phone: '',
                address: ''
            },
            // 用户id
            id: 0,
            // 表单验证
            rulesForm: {
                name: [
                    { required: true, message: '请填写姓名', trigger: 'blur' }
                ],
                age: [
                    { validator: checkAge, trigger: 'blur' }
                ],
                phone: [
                    { validator: checkPhone, trigger: 'blur' }
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
            this.$refs.ruleForm.validate(async (valid) => {
                if (valid) {
                    const res = await eidtUser(this.form)
                    if (res.code !== "200") return this.$message.error("修改数据失败")
                    this.$message.success("修改成功")
                    this.$router.push("/user")
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