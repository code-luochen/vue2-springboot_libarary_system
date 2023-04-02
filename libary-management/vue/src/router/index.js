import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from "@/views/Layout.vue"
import Cookies from 'js-cookie'

Vue.use(VueRouter)

const routes = [
  // 登录页面
  {
    path: '/login',
    name: 'login',
    component: () => import("@/views/login/Login.vue")
  },
  // 布局路由
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect:"/home",
    children: [
      //  ===== 主页 =====
      {
        path: '/home',
        name: 'home',
        component: () => import("@/views/HomeView.vue"),
      },

      //  ==== User ====
      // 会员管理的页面
      {
        path: 'user',
        name: 'user',
        component: () => import("@/views/user/User.vue")
      },
      // 添加会员界面
      {
        path: "adduser",
        name: 'adduser',
        component: () => import("@/views/user/AddUser.vue")
      },
      // 编辑用户
      {
        path: "edituser",
        name: 'EditUser',
        component: () => import("@/views/user/EditUser.vue")
      },

      //   ==== Admin ====

      {
        path: 'admin',
        name: 'Admin',
        component: () => import("@/views/admin/List.vue")
      },
      {
        path: "addAdmin",
        name: 'AddAdmin',
        component: () => import("@/views/admin/Add.vue")
      },
      {
        path: "editAdmin",
        name: 'EditAdmin',
        component: () => import("@/views/admin/Edit.vue")
      },

      //   ==== Category ====

      {
        path: 'category',
        name: 'CategoryList',
        component: () => import("@/views/category/List.vue")
      },
      {
        path: "addCategory",
        name: 'AddCategory',
        component: () => import("@/views/category/Add.vue")
      },
      {
        path: "editCategory",
        name: 'EditCategory',
        component: () => import("@/views/category/Edit.vue")
      },

       //   ==== Book ====

       {
        path: 'book',
        name: 'BookList',
        component: () => import("@/views/book/List.vue")
      },
      {
        path: "addBook",
        name: 'AddBook',
        component: () => import("@/views/book/Add.vue")
      },
      {
        path: "editBook",
        name: 'EditBook',
        component: () => import("@/views/book/Edit.vue")
      },

       //   ==== Borrow ====

       {
        path: 'borrow',
        name: 'BorrowList',
        component: () => import("@/views/borrow/List.vue")
      },
      {
        path: "addBorrow",
        name: 'AddBorrow',
        component: () => import("@/views/borrow/Add.vue")
      },
      {
        path: "editBorrow",
        name: 'EditBorrow',
        component: () => import("@/views/borrow/Edit.vue")
      },

      //   ==== Return ====

      {
       path: 'returnlist',
       name: 'returnlist',
       component: () => import("@/views/retur/List.vue")
     }
    ]
  },
  //当路由不存在时显示的页面
{
  path:'*',
  component:()=>import("@/views/404.vue")
}

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
// 路由守卫
router.beforeEach((to,from,next)=>{
  if(to.path=="/login") next()
  const admin = Cookies.get('admin')
  if(!admin && to.path!=="/login") return next("/login")
  next()

})
export default router
