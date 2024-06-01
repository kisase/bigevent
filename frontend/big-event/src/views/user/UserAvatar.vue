<script setup>
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'
//获取请求头token值
import { userTokenStore } from '@/stores/token.js'
const userToken = userTokenStore();

//用户头像地址
import useUserInfoStore from '@/stores/userinfo.js'
const userInfoStore = useUserInfoStore();
const imageUrl = ref(userInfoStore.info.userpic)

const uploadRef = ref()

//文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
const handlePreview = (result) => {
    imageUrl.value = URL.createObjectURL(result.raw)
    // console.log(URL.createObjectURL(result.raw))
}
//上传成功的回调函数
import { ElMessage } from 'element-plus'
import {userupdataAvatarService} from '@/api/userApi.js'
const handleAvatarSuccess = (result) => {
    imageUrl.value = result.data 
    //修改头像
    let res =userupdataAvatarService(imageUrl.value);
    //更改pinia的值
    userInfoStore.info.userpic = imageUrl.value;
    ElMessage({
        type: 'success',
        message: res.message?res.message:'修改成功',
    })
}


//上传图片并修改头像地址

const submitUpload = () => {
    uploadRef.value.submit()
    
}


</script>


<template>
    <el-card>
        <template #header>
            <div class="card-header">
                <span>更换头像</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false"
                    :on-success="handleAvatarSuccess" ref="uploadRef" :headers="{ Authorization: userToken.token }"
                    :auto-upload="false" :on-change="handlePreview">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
                <br />

                <el-button type="primary" @click="uploadRef.$el.querySelector('input').click()">选择图片</el-button>
                <el-button type="success" @click="submitUpload">上传图片</el-button>

            </el-col>



        </el-row>


    </el-card>
</template>

<style lang="scss" scoped>
.el-card {
    .avatar-uploader {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
        }
    }


}
</style>