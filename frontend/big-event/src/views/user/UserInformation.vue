<script  setup>
import { ref } from 'vue';

// import {userInfoService} from '@/api/userApi';
// const userinfoData = userInfoService();
import userUserInfoStore from '@/stores/userinfo.js'
import { updataUserinfoService } from '@/api/userApi';
import { ElMessage } from 'element-plus';
const UserInfoStore = userUserInfoStore();

// 定义数据模型
const userData = ref({...UserInfoStore.info})

//定义表单校验规则
const rules = {
    nickname: [
        { required: true, message: "请输入用户昵称", trigger: "blur" },
        { min:2,max:10, message: "长度为2~10位非空字符！", trigger: "blur" }
    ],
    email: [
        { required: true, message: "请输入邮箱账号！", trigger: "blur" },
        { type: 'email', message: '邮箱格式不正确！', trigger: "blur" }
    ]
}
//提交修改个人信息
const onSubmit = async () => {
  
  let result = await updataUserinfoService(userData.value);
//成功
  ElMessage.success(result.message ? result.message : "注册成功");
  //修改pinia中的信息
  UserInfoStore.setInfo(userData.value);
}
</script>


<template>
    <el-card>
        <template #header>
            <div class="card-header">
                <span>基本资料</span>
            </div>
        </template>
        <el-form label-width="auto" style="max-width: 600px" :model="userData" :rules="rules">
            <el-form-item label="登录名称">
                <el-input disabled v-model="userData.username" />
            </el-form-item>
            <el-form-item label="用户昵称" prop="nickname">
                <el-input v-model="userData.nickname" />
            </el-form-item>
            <el-form-item label="用户邮箱" prop="email">
                <el-input v-model="userData.email" />
            </el-form-item>
            <el-form-item label=" ">
                <el-button type="primary" @click="onSubmit">
                    提交修改
                </el-button>

            </el-form-item>
        </el-form>

    </el-card>
</template>

<style lang="scss" scoped>
.el-card {
    height: 99%;
}
</style>