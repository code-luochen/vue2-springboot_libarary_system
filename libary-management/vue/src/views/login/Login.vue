<template>
    <div class="container">


        <el-card class="cover" v-if="loginAdmin.id">
            <slide-verify :l="42" :r="10" :w="310" :h="155" slider-text="向右滑动" @success="onSuccess" @fail="onFail"
                @refresh="onRefresh"></slide-verify>
        </el-card>


        <div class="box">
            <el-form ref="LoginFormRef" :model="admin" :rules="rulesForm">
                <div class="texttip">登 录</div>
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" size="mediu" prefix-icon="el-icon-user"
                        v-model="admin.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" show-password size="mediu" prefix-icon="el-icon-lock"
                        v-model="admin.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="mediu" style="width:100%;" @click="Login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { login } from "@/api/requireadmin"
import Cookies from 'js-cookie'
export default {
    name: 'Login',
    data() {
        return {
            admin: {
                username: '',
                password: ''
            },
            loginAdmin: {},
            rulesForm: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3-20个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在3-10个字符', trigger: 'blur' }
                ],

            }
        }
    },
    methods: {
        // 登录
        Login() {
            this.$refs.LoginFormRef.validate(async (valid) => {
                if (valid) {
                    const res = await login(this.admin)
                    if (res.code !== '200') return this.$message.error(res.msg)
                    this.loginAdmin = res.data    //如果成功滑块组件就出现了
                } else {
                    this.$message.error(res.msg)
                }
            });
        },
        // 滑块的三个函数
        onSuccess() {//滑块验证通过后触发
            Cookies.set('admin', JSON.stringify(this.loginAdmin))
            this.$router.push("/")
            this.$message.success("登录成功")
        },
        onFail() {
            
        },
        onRefresh() {
            
        }
    }
}
</script>

<style lang="less" scoped>
.container {
    height: 100vh;
    padding-top: 150px;
    position: relative;

    .box {
        height: 400px;
        width: 500px;
        background-color: white;
        border-radius: 10px;
        margin: auto;
        padding: 50px;
    }

    .texttip {
        margin: 30px;
        text-align: center;
        font-weight: bold;
        color: dodgerblue;
        font-size: 30px;
    }

    .cover {
        width: fit-content;
        background-color: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 1000;
    }
}
</style>