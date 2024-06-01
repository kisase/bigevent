import { defineStore } from "pinia";
import { ref } from "vue";

/**
 * 第一个参数：名字，唯一性
 * 第二个参数：函数，函数的内部可以定义状态的所有内容
 * 
 * 返回值：函数
 */
export const userTokenStore = defineStore('token',()=>{
    //定义描述token的状态
    const token = ref('')

    //定义修改token的方法
    const setToken = (newToken)=>{
        token.value = newToken
    }

    //定义移除token的方法
    const removeToken = ()=>{
        token.value = ''
    }
    return {
        token,setToken,removeToken
    }
},{
    persist:true  //持久存储
});
