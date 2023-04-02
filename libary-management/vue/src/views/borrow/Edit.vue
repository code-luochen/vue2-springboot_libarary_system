<template>
    <div class="box">
        <div class="adduser">编辑图书</div>
        <el-form :inline="true" class="form" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
            <el-form-item label="图书标准码" prop="bookNo">
                <el-select v-model="form.bookNo" filterable placeholder="请选择" @change="selBook">
                    <el-option v-for="item in books" :key="item.id" :label="item.bookNo" :value="item.bookNo">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="图书名称" prop="bookName">
                <el-input  v-model="form.bookName" disabled placeholder="请输入图书名称"></el-input>
            </el-form-item>
            <el-form-item label="所需积分" prop="score">
                <el-input v-model="form.score" disabled></el-input>
            </el-form-item>
            <el-form-item label="图书数量" prop="nums">
                <el-input v-model="form.nums" disabled></el-input>
            </el-form-item>
            <br>
            <el-form-item label="会员码" prop="userNo" >
                <el-select v-model="form.userNo" filterable placeholder="请选择" @change="selUser">
                    <el-option v-for="item in users" :key="item.id" :label="item.username" :value="item.username">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="用户名称" prop="userName">
                <el-input v-model="form.userName" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户联系方式" prop="userPhone">
                <el-input v-model="form.userPhone" disabled></el-input>
            </el-form-item>
           
            <el-form-item label="用户积分" prop="account">
                <el-input v-model="form.account" disabled></el-input>
            </el-form-item>
        </el-form>
        <div class="btn">
            <el-button type="primary" @click="update" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { getById, eidtCategory,getCategories,getBooklist ,getUserlist } from "@/api/requireborrow"
export default {
    name: 'EditBook',
    data() {
        return {
            // 表单数据
            form: {},
            books: [],
            users:[],
            // 用户id
            id: 0,
            // 表单验证
            rules:{
                userNo: [
                    { required: true, message: '请输入会员码', trigger: 'blur' }
                ],
                bookNo:[
                { required: true, message: '请输入图书标准码', trigger: 'blur' }
                ]
             }
        }
    },
    created() {
        this.Categories()
        this.getBooks()
        this.getUsers()

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
                    this.$router.push("/borrow")
                } else {
                    this.$message.info("请按要求填写内容")
                    return false;
                }
            });
        },
         // 获取分类
         async Categories(){
            const res = await getCategories()
            this.categories=res.data

        },
         // 获取图书列表
         async getBooks(){
            const res = await getBooklist()
            this.books=res.data
        },
        // 获取用户的列表
        async getUsers(){
            const res = await getUserlist()
            this.users= res.data
        }, // 在选择了标准码后将图书的名称带出来
        selBook(){
           this.form.bookName= this.books.find(x=>x.bookNo===this.form.bookNo).name
           this.form.score= this.books.find(x=>x.bookNo===this.form.bookNo).score
           this.form.nums= this.books.find(x=>x.bookNo===this.form.bookNo).nums
        },
        // 选择借书的用户   
        selUser(){
            this.form.userName=this.users.find(x=>x.username===this.form.userNo).name
            this.form.userPhone=this.users.find(x=>x.username===this.form.userNo).phone
            this.form.account=this.users.find(x=>x.username===this.form.userNo).account
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