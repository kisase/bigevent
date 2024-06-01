/**
 * axios拦截器
 */


//导入axois
import axios from "axios";
import router from '@/router'
import { ElMessage } from 'element-plus'

//定义一个变量，记录公共前缀
//const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({ baseURL })
//token
import { userTokenStore } from "@/stores/token";
// 添加请求拦截器

instance.interceptors.request.use(
  function (config) {
    //添加请求头
    const tokenStore = userTokenStore();
    if (tokenStore.token) {
      config.headers.Authorization = tokenStore.token
    }
    return config;
  },
  function (error) {
    ElMessage.error("服务异常！");
    return Promise.reject(error);
  });

// 添加响应拦截器
instance.interceptors.response.use(
  function (result) {
    //判断业务状态码
    if (result.data.code === 200) {
      return result.data;
    }
    //操作失败
    ElMessage.error(result.data.message ? result.data.message : "服务异常！")
    return Promise.reject(result.data)
  },
  function (error) {
    if(error.response.status == 401){
      ElMessage.error("请先登录")
      router.push('/login')
    }else{
      ElMessage.error("服务异常！")
    }

    return Promise.reject(error);
  });

//导出instance
export default instance;