import axios from "axios";
import Cookies from "js-cookie";
import router from "@/router"

// 后台的地址
export const url = "http://localhost:9090/api"

const request = axios.create({
    baseURL:url,
    timeout:5000
})

// 添加请求拦截器
// 可以在信息发送前对做一些处理，比如统一添加token，对请求的参数进行统一的加密
request.interceptors.request.use(config=>{
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
  
    const adminJson = Cookies.get('admin')
    if(adminJson){
        //设置请求头
          config.headers['token'] = JSON.parse(adminJson).token; 
    }


    return config;
},error=>{
    return Promise.reject(error)
})
// 添加响应拦截器
 request.interceptors.response.use(response=>{
    let res = response.data;
    // 兼容服务器端返回的字符串数据
    if(typeof res === 'string'){
        res = res ? JSON.parse(res) : res;
    }
    if(res.code === "401"){
        router.push('/login')
    }
    return res;
 },error=>{
    console.log("err"+error);
    return Promise.reject(error)
 })

 export default request