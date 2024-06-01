<template>
  <div class="background">
    <el-row>
      <el-col :span="9"></el-col>
      <el-col :span="6">
        <!-- 注册表单 -->
        <!-- 不记录输入过信息写入autocomplete="off" -->
        <el-form ref="registerForm" autocomplete="off" v-if="!isRegister" size="large" :model="registerData" :rules="rules"
          class="demo-registerData">
          <el-form-item>
            <h1>注册</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="registerData.username" placeholder="请输入账号" :prefix-icon="User" type="username" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="registerData.password" placeholder="请输入密码" :prefix-icon="Lock" type="password" />
          </el-form-item>
          <el-form-item prop="repassword">
            <el-input v-model="registerData.repassword" placeholder="请再次输入密码" :prefix-icon="Lock" type="repassword" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%;" @click="register">注册</el-button>
          </el-form-item>
          <el-form-item>
            <el-link type="primary" :underline="false" auto-insert-space @click="isRegister = true; clearregisterData()">
              ←返回
            </el-link>
          </el-form-item>
        </el-form>
        <!-- 登录表单 -->
        <el-form ref="loginForm" autocomplete="off" v-if="isRegister" size="large" :model="registerData" :rules="rules"
          class="demo-registerData">
          <el-form-item>
            <h1>登录</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="registerData.username" placeholder="请输入账号" :prefix-icon="User" type="username" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="registerData.password" placeholder="请输入密码" :prefix-icon="Lock" type="password" />
          </el-form-item>
          <el-form-item>
            <el-col :span="6"><el-checkbox>记住密码</el-checkbox></el-col>
            <el-col :span="6" :offset="12"><el-link type="primary" :underline="false">忘记密码?</el-link></el-col>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%;" @click="login()">登录</el-button>
          </el-form-item>
          <el-form-item>
            <el-link type="primary" :underline="false" @click="isRegister = false; clearregisterData()">
              ←注册
            </el-link>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

  </div>
</template>
  
<script setup>

import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const registerForm = ref(null); // 注册表单的 ref
const loginForm = ref(null); // 登录表单的 ref


//登录与注册表单的显示，默认登录表单
const isRegister = ref(true)

// 定义数据模型
const registerData = ref({
  username: '',
  password: '',
  repassword: '',
})
// 校验密码规则的函数
const checkRePassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请再次输入密码"));
  } else if (value !== registerData.value.password) {
    callback(new Error("两次密码不一致!"))
  } else {
    callback()
  }
}

//定义表单校验规则
const rules = {
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 5, max: 16, message: "长度为5~16位非空字符！", trigger: "blur" }
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 5, max: 16, message: "长度为5~16位非空字符！", trigger: "blur" }
  ],
  repassword: [
    { validator: checkRePassword, trigger: "blur" },
  ]
}

import { userRegisterService, userLoginService } from '@/api/userApi';
//注册表单提交数据响应
const register = async () => {
  const isValid = await validateRegister();
  if (!isValid) {
    return;
  }
  //成功
  let result = await userRegisterService(registerData.value);
  // if (result.code == 200) {
  //   alert(result.message ? result.message : "注册成功");
  // } else {
  //   //失败
  //   alert(result.message)
  // }
  ElMessage.success(result.message ? result.message : "注册成功");
}

//导入token状态
import { userTokenStore } from '@/stores/token.js'
import router from '@/router';

//调用userTokenStore得到状态
const tokenStore = userTokenStore();

//登录校验
const login = async () => {
  const isValid = await validateLogin();
  if (!isValid) {
    return;
  }
  //成功
  let result = await userLoginService(registerData.value);
  // if (result.code == 200) {
  //   alert(result.message ? result.message : "登录成功");
  // } else {
  //   //失败
  //   alert(result.message)
  // }
  ElMessage.success(result.message ? result.message : "登录成功")
  //把token值存储到pinia
  tokenStore.setToken(result.data)
  //路由跳转
  router.push('/')

}

const validateRegister = async () => {
  try {
    await registerForm.value.validate();
    return true;
  } catch (error) {
    console.error('表单验证失败');
    return false;
  }
}

const validateLogin = async () => {
  try {
    await loginForm.value.validate();
    return true;
  } catch (error) {
    console.error('表单验证失败');
    return false;
  }
}
//定义清空数据模型的数值
const clearregisterData = () => {
  registerData.value = {
    username: '',
    password: '',
    repassword: '',
  }
}

</script>
  
<style scoped>
.background {
  background: url("../assets/123.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}

.el-form {
  margin: 100px auto;
  padding: 10px;
  border: 1px solid #dbdbdbd3;
  border-radius: 20px;
  box-shadow: 0 0 25px #d6d6d6;
  background-color: rgb(255, 255, 255, 0.5);
}
</style>