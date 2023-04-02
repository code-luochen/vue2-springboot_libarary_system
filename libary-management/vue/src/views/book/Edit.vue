<template>
    <div class="box">
        <div class="adduser">编辑图书</div>
        <el-form :inline="true" class="form" :rules="rules" ref="rulesForm" :model="form" label-width="100px">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
                <el-input type="textarea" v-model="form.description" placeholder="请填写书籍描述"></el-input>
            </el-form-item>
            <el-form-item label="出版日期" prop="publishDate">
                <el-date-picker v-model="form.publishDate" value-format="yyyy-MM-dd" type="date" placeholder="请选择出版日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="作者" prop="author">
                <el-input v-model="form.author" placeholder="请输入作者"></el-input>
            </el-form-item>
            <el-form-item label="出版社" prop="publisher">
                <el-input v-model="form.publisher" placeholder="请输入出版社"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="category">
                <el-cascader v-model="form.categories" :props="{value:'name',label:'name'}" :options="categories" ></el-cascader>
            </el-form-item>
            <el-form-item label="标准码" prop="bookNo">
                <el-input v-model="form.bookNo" placeholder="请输入备注"></el-input>
            </el-form-item>
            <el-form-item label="借书积分" prop="score">
                <el-input-number v-model="form.score" :min="10" :max="30" label="借书积分"></el-input-number>
            </el-form-item>
            <br>
            <el-form-item label="封面" prop="cover">
                <el-upload class="avatar-uploader" :action="Url+'/book/file/upload?token='+this.admin.token"
                    :show-file-list="false" :on-success="handleCoverSuccess" >
                    <img v-if="form.cover" :src="form.cover" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
        </el-form>

        <div class="btn">
            <el-button type="primary" @click="update" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { getById, eidtCategory,getCategories } from "@/api/requirebook"
import { url } from "@/utils/request"
import Cookies from 'js-cookie'
export default {
    name: 'EditBook',
    data() {
        return {
            admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
            // 网络地址
            Url:url,
            // 表单数据
            form: {score:10,cover:''},
            // 用户id
            id: 0,
            categories:[],
            // 表单验证
            rules: {
                name: [
                    { required: true, message: '请输入图书名称', trigger: 'blur' }
                ],
                score:[
                { required: true, message: '请输入借书积分', trigger: 'blur' }
                ],
                bookNo:[
                { required: true, message: '请输入图书标准码', trigger: 'blur' }
                ]

            }
        }
    },
    created() {
        this.Categories()

        this.id = this.$route.query.id
        this.queryById()
    },
    methods: {

         // 上传头像
         handleCoverSuccess(res){
            if(res.code==="200"){
                 this.form.cover=res.data
            }
        },
        // 根据id查询数据
        async queryById() {
            const res = await getById(this.id)
            if (res.code !== "200") return this.$message.error("获取数据失败")
            this.form = res.data
            if(this.form.category){
                // 将原来的字符串数据分割成数组
                this.form.categories=this.form.category.split(" > ")
            }
        },
        // 提交更新数据
        async update() {
            this.$refs.rulesForm.validate(async (valid) => {
                if (valid) {
                    const res = await eidtCategory(this.form)
                    if (res.code !== "200") return this.$message.error("修改数据失败")
                    this.$message.success("修改成功")
                    this.$router.push("/book")
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

.avatar-uploader  {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>