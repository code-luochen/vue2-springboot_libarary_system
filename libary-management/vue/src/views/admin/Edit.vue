<template>
    <div class="box">
        <div class="adduser">编辑管理员</div>
        <el-form :inline="true" class="form" :rules="rulesForm" ref="ruleForm"  :model="form" label-width="100px">
            <el-form-item label="管理员">
                <el-input v-model="form.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="update" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import {getById,eidtAdmin} from "@/api/requireadmin"
export default {
    name: 'EditAdmin',
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
                phone:'',
                email:''
            },
            // 用户id
            id:0,
             // 表单验证
             rulesForm: {
                
                phone:[
                     { validator: checkPhone, trigger: 'blur' }
                ]

            }
        }
    },
    created(){
        this.id = this.$route.query.id
        this.queryById()
    },
    methods:{
        // 根据id查询数据
        async queryById(){
            const res = await getById(this.id)
            if(res.code!=="200") return this.$message.error("获取数据失败")
            this.form=res.data
        },

        // 提交更新数据
       async update(){
            const res = await eidtAdmin(this.form)
            if(res.code !=="200") return this.$message.error("修改数据失败")
            this.$message.success("修改成功")
            this.$router.push("/admin")
        },
        // 清空表单
        clean(){
            this.form={}
        }

    }
}
</script>

<style lang="less" scoped>
.box{
    display: block;
    width: 80%;
}
.adduser {
    margin-bottom: 30px;
}
.btn{
    text-align: center;
    margin-top: 30px;
}
</style>