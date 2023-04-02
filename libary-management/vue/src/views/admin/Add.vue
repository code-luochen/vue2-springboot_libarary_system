<template>
    <div class="box">
        <div class="adduser">新增管理员</div>
        <el-form :inline="true" class="form" :rules="rulesForm" ref="ruleRef" :model="form" label-width="100px">
            <el-form-item label="管理员" prop="username">
                <el-input v-model="form.username" placeholder="请输入管理员"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="save" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { saveAdmin } from "@/api/requireadmin"
export default {
    name: 'AddAdmin',
    data() {
       
        // 手机号码自定义校检规则
        const checkPhone =(rule, value, callback) => {
            if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
                return callback(new Error('请输入合法的电话号码'));
            }
            // 输入成功后也有一个回调函数
            callback()
        };
        return {
            // 表单数据
            form: {
                username: '',
                phone: '',
                emial: ''
            },
            // 表单验证
            rulesForm: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    {min:3,max:10,message: '长度在3-10个字符',trigger:'blur'}
                ],
                phone:[
                     { validator: checkPhone, trigger: 'blur' }
                ]

            }
        }
    },
    methods: {
        // 添加新用户
        save() {
            this.$refs.ruleRef.validate(async (valid) => {
                if (valid) {
                    const res = await saveAdmin(this.form)
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