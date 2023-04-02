<template>
  <div>
    <!-- 头部区域 -->
    <div class="head">
      <div class="topictitle">
        <img src="@/assets/logo .png" alt="">
        <span>图书管理系统</span>
      </div>
      <div class="userlogout">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ admin.username }}管理员<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <div class="text_gap">
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div class="text" @click="logout">退出登录</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </div>

        </el-dropdown>
      </div>

    </div>
    <!-- 侧边栏和主体 -->
    <div class="box">
      <!-- 侧边栏导航 -->
      <div class="aside">
        <el-menu :default-active="$route.path" router unique-opened menu-trigger="click">
          <el-menu-item index="/">
            <i class="el-icon-eleme"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-question"></i>
              <span>会员管理</span>
            </template>

            <el-menu-item index="/adduser">
              <i class="el-icon-menu"></i>
              <span slot="title">会员添加</span>
            </el-menu-item>
            <el-menu-item index="/user">
              <i class="el-icon-menu"></i>
              <span slot="title">会员列表</span>
            </el-menu-item>
          </el-submenu>
          <!-- 管理员 -->
          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>

            <el-menu-item index="/addAdmin">
              <i class="el-icon-menu"></i>
              <span slot="title">管理员添加</span>
            </el-menu-item>
            <el-menu-item index="/admin">
              <i class="el-icon-menu"></i>
              <span slot="title">管理员列表</span>
            </el-menu-item>
          </el-submenu>
          <!-- 分类 -->
          <el-submenu index="category">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>图书分类管理</span>
            </template>

            <el-menu-item index="/addCategory">
              <i class="el-icon-menu"></i>
              <span slot="title">图书分类添加</span>
            </el-menu-item>
            <el-menu-item index="/category">
              <i class="el-icon-menu"></i>
              <span slot="title">图书分类列表</span>
            </el-menu-item>
          </el-submenu>

          <!-- 图书 -->
          <el-submenu index="book">
            <template slot="title">
              <i class="el-icon-notebook-1"></i>
              <span>图书管理</span>
            </template>

            <el-menu-item index="/addBook">
              <i class="el-icon-menu"></i>
              <span slot="title">图书添加</span>
            </el-menu-item>
            <el-menu-item index="/book">
              <i class="el-icon-menu"></i>
              <span slot="title">图书列表</span>
            </el-menu-item>
          </el-submenu>

          <!-- 借书 -->
          <el-submenu index="borrow">
            <template slot="title">
              <i class="el-icon-document-copy"></i>
              <span>借书管理</span>
            </template>

            <el-menu-item index="/addBorrow">
              <i class="el-icon-menu"></i>
              <span slot="title">借书添加</span>
            </el-menu-item>
            <el-menu-item index="/borrow">
              <i class="el-icon-menu"></i>
              <span slot="title">借书列表</span>
            </el-menu-item>
          </el-submenu>

          <!-- 还书 -->
          <el-submenu index="retur">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>还书管理</span>
            </template>

            <el-menu-item index="/returnlist">
              <i class="el-icon-menu"></i>
              <span slot="title">还书列表</span>
            </el-menu-item>
            
          </el-submenu>
        </el-menu>
      </div>
      <!-- 内容数据 -->
      <div class="item">
        <router-view />
      </div>
    </div>

  </div>
</template>

<script>
import Cookies from 'js-cookie'
export default {
  name: 'LayOut',
  data() {
    return {
      // 登录后的用户对象，这个对象可能有，也可能没有
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  methods: {
    logout() {
      // 清空浏览器数据
      Cookies.remove("admin")
      this.$router.push("/login")
    }
  }
}

</script>

<style lang="less" scoped>
.head {
  height: 60px;
  line-height: 60px;
  background-color: white;
  margin-bottom: 2px;
  display: flex;

  .topictitle {
    width: 300px;
  }

  .userlogout {
    flex: 1;
    text-align: right;
    padding-right: 40px;

    .el-dropdown-link {
      font-size: 18px;
      cursor: pointer;
    }

    .text_gap {
      margin-top: -15px;
    }
  }

  img {
    width: 40px;
    position: relative;
    top: 10px;
    left: 20px;
  }

  span {
    margin-left: 25px;
    font-size: 24px
  }
}

.box {
  display: flex;

  .aside {
    width: 200px;
    overflow: hidden;
    min-height: calc(100vh - 62px);
    margin-right: 2px;
    background-color: white;
  }
}

// 路由内容区域
.item {
  flex: 1;
  background-color: white;
  padding: 10px;
}
</style>