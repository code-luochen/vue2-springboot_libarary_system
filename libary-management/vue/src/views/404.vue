<template>
    <div class="container">
        <div class="box">
            404
        </div>
        <div class="text">
            未找到页面
        </div>
        <div class="btn" @click="$router.push('/')">
            返回主页
        </div>
    </div>
</template>

<script>

</script>

<style lang="less" scoped>
.container{
    text-align: center;
    .box{
margin-top: 150px;
font-size: 100px;
}
.text{
    font-size: 50px;
}
.btn{
    margin-top: 20px;
    cursor: pointer;
    color: dodgerblue;
}
}

</style>