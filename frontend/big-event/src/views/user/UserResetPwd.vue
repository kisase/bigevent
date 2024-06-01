<script  setup>
import { ref } from 'vue';

// 校验密码规则的函数
const checkRePassword = (rules, value, callback) => {
    if (value === "") {
        callback(new Error("请再次输入密码"));
    } else if (value !== pwdData.value.re_Pwd) {
        callback(new Error("两次密码不一致!"))
    } else {
        callback()
    }
}

//定义数据模型
const pwdData = ref({
    old_Pwd: '',
    new_Pwd: '',
    re_Pwd: ''
})


//定义表单校验规则
const rules = {
    old_Pwd: [
        { required: true, message: "请输入密码", trigger: "blur" },
        { min: 5, max: 16, message: "长度为5~16位非空字符！", trigger: "blur" }
    ],
    new_Pwd: [
        { required: true, message: "请输入新密码", trigger: "blur" },
        { min: 5, max: 16, message: "长度为5~16位非空字符！", trigger: "blur" }
    ],
    re_Pwd: [
        { required: true, message: "请再次输入新密码", trigger: "blur" },
        { validator: checkRePassword, trigger: "blur" },
    ]
}

//修改密码
import router from '@/router';
import { userupdataPasswordService } from '@/api/userApi';
import { ElMessage } from 'element-plus'
//调用pinia，退出清空 token状态
import { userTokenStore } from '@/stores/token';
const tokenStroe = userTokenStore();
import useUserInfoStore from '@/stores/userinfo.js'
const userInfoStore = useUserInfoStore();
const updataPwd = async () => {
    let result = await userupdataPasswordService(pwdData.value)
    ElMessage.success(result.message ? result.message : "修改成功");
    //退出登录
    //清除pinia中的数据
    tokenStroe.removeToken();
    userInfoStore.removeInfo()
    //跳转登录页
    router.push('/login')
}

</script>


<template>
    <el-card>
        <template #header>
            <div class="card-header">
                <span>更改密码</span>
            </div>
        </template>
        <el-form label-width="auto" style="max-width: 600px" :model="pwdData" :rules="rules">
            <el-form-item label="原密码" prop="old_Pwd">
                <el-input type="password" v-model="pwdData.old_Pwd" />
            </el-form-item>
            <el-form-item label="新密码" prop="new_Pwd">
                <el-input type="password" v-model="pwdData.new_Pwd" />
            </el-form-item>
            <el-form-item label="确认密码" prop="re_Pwd">
                <el-input type="password" v-model="pwdData.re_Pwd" />
            </el-form-item>
            <el-form-item label=" ">
                <el-button type="primary" @click="updataPwd">
                    提交修改
                </el-button>

            </el-form-item>
        </el-form>

    </el-card>
</template>

<style lang="scss" scoped>

</style>