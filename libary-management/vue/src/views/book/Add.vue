<template>
    <div class="box">
        <div class="adduser">新增图书</div>
        <el-form :inline="true" class="form" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
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
                <el-cascader v-model="form.categories" :props="{ value: 'name', label: 'name' }"
                    :options="categories"></el-cascader>
            </el-form-item>
            <el-form-item label="标准码" prop="bookNo">
                <el-input v-model="form.bookNo" placeholder="请输入备注"></el-input>
            </el-form-item>
            <el-form-item label="借书积分" prop="score">
                <el-input-number v-model="form.score" :min="10" :max="30" label="借书积分"></el-input-number>
            </el-form-item>
            <el-form-item label="图书数量" prop="nums">
                <el-input v-model="form.nums" placeholder="请输入图书数量"></el-input>
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
            <el-button type="primary" @click="save" size="mediu">提交</el-button>
        </div>
    </div>
</template>

<script>
import { saveCategory, getCategories } from "@/api/requirebook"

import { url } from "@/utils/request"
import Cookies from 'js-cookie'
export default {
    name: 'AddBook',
    data() {

        const checkNums = (rule, value, callback) => {
            value = parseInt(value)
            if (value < 0 || value > 100) {
                return callback(new Error('请输入大于等于0小于1000的整数'));
            }
            // 输入成功后也有一个回调函数
            callback()
        };
        return {
            // 网络地址
            Url:url,
            admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
            // 表单数据
            form: { score: 10 ,cover:''},
            categories: [],
            // 表单验证
            rules: {
                name: [
                    { required: true, message: '请输入图书名称', trigger: 'blur' }
                ],
                score: [
                    { validator: checkNums, trigger: 'blur' }
                ],
                bookNo: [
                    { required: true, message: '请输入图书标准码', trigger: 'blur' }
                ],
                nums: [
                    { required: true, message: '请输入数量', trigger: 'blur' },
                    { validator: checkNums, trigger: 'blur' }
                ]

            }
        }
    },
    created() {
        this.Categories()
    },
    methods: {

        // 上传头像
        handleCoverSuccess(res){
            if(res.code==="200"){
                 this.form.cover=res.data
                 console.log(res.data);
                //  console.log(this.form.cover);
            }
        },
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
        // 获取分类
        async Categories() {
            const res = await getCategories()
            this.categories = res.data
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