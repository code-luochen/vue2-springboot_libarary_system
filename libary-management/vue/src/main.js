import Vue from 'vue'
import App from './App.vue'
import router from './router'

// 引入滑块组件
import SlideVerify from 'vue-monoplasty-slide-verify';

Vue.use(SlideVerify);

// 引入组件库
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false

// 引入全局css样式
import "@/assets/css/global.css"

Vue.use(ElementUI,{size:"small"});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
