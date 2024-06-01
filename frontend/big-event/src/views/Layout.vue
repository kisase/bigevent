<template>
  <div class="common-layout">
    <el-container class="layout-container-demo">
      <!-- 头部 -->
      <el-header>
        <!-- 头左边 -->
        <div class="left">
          <div class="imgtitle" style="width: 180px;"></div>
          <el-icon v-if="isCollapse == false" @click="isLR" color="white">
            <Fold />
          </el-icon>
          <el-icon v-else @click="isLR" color="white">
            <Expand />
          </el-icon>
        </div>
        <!-- 头右边 -->
        <div class="toolbar">
          <span>{{ userInfoStore.info.nickname }}</span>
          <el-dropdown class="unloggen-btn" placement="bottom-end" @command="handleCommand">
            <div style="margin: 8px; ">
              <el-avatar :size="40" :src="userInfoStore.info.userpic ? userInfoStore.info.userpic : acatar">
              </el-avatar>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="info">
                  <el-icon>
                    <User />
                  </el-icon>
                  基本资料
                </el-dropdown-item>
                <el-dropdown-item command="avatar">
                  <el-icon>
                    <PictureRounded />
                  </el-icon>
                  更换头像
                </el-dropdown-item>
                <el-dropdown-item command="resetPwd">
                  <el-icon>
                    <EditPen />
                  </el-icon>
                  重置密码
                </el-dropdown-item>
                <el-dropdown-item command="logout">
                  <el-icon>
                    <SwitchButton />
                  </el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>

        </div>
      </el-header>
      <el-container>
        <!-- 左边  -->
        <el-aside :width="isCollapse?'65px': '200px'">
          <el-menu :router="true" default-active="/article/manage" class="el-menu-vertical-demo" background-color="#545c64"
            active-text-color="#ffd04b" text-color="#fff" :collapse="isCollapse">

            <el-menu-item index="/article/home">
              <el-icon>
                <Management />
              </el-icon>
              <template #title>首页</template>
            </el-menu-item>
            <el-menu-item index="/article/category">
              <el-icon>
                <Management />
              </el-icon>
              <template #title>文章分类</template>
            </el-menu-item>
            <el-menu-item index="/article/manage">
              <el-icon>
                <Promotion />
              </el-icon>
              <template #title>文章管理</template>
            </el-menu-item>
            <el-sub-menu>
              <template #title>
                <el-icon>
                  <UserFilled />
                </el-icon>
                <span>个人中心</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/user/info">
                  <el-icon>
                    <User />
                  </el-icon>
                  <span>基础资料</span>
                </el-menu-item>

                <el-menu-item index="/user/avatar">
                  <el-icon>
                    <PictureRounded />
                  </el-icon>
                  <span>头像更换</span>
                </el-menu-item>

                <el-menu-item index="/user/resetPwd">
                  <el-icon>
                    <EditPen />
                  </el-icon>
                  <span>重置密码</span>
                </el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>


          </el-menu>
        </el-aside>
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>
import acatar from '@/assets/1.jpg'
import { ref } from 'vue';
const isCollapse = ref(false);

const isLR = () => {
  isCollapse.value = !isCollapse.value ;
}

import router from '@/router';
import { ElMessage, ElMessageBox } from 'element-plus';
//调用pinia，退出清空 token状态
import { userTokenStore } from '@/stores/token';
const tokenStroe = userTokenStore();

//获取用户信息
import { userInfoService } from '@/api/userApi';
const getUserInfo = async () => {
  let result = await userInfoService();
  //保存用户信息到pinia
  userInfoStore.setInfo(result.data)
}
getUserInfo();

//调用用户在pinia的信息
import useUserInfoStore from '@/stores/userinfo.js'
const userInfoStore = useUserInfoStore();


//条目被点击后
const handleCommand = (command) => {
  if (command == "logout") {
    ElMessageBox.confirm(
      '你确定要退出吗？',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
      .then(async () => {
        //退出登录
        //清除pinia中的数据
        tokenStroe.removeToken();
        userInfoStore.removeInfo()
        //跳转登录页
        router.push('/login')
        ElMessage({
          type: 'success',
          message: '退出登录成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '你取消了退出登录',
        })
      })

  } else {
    //路由
    router.push('/user/' + command)
  }
}


</script>


<style lang="scss" scoped>
.common-layout {

  .layout-container-demo {
    height: 97vh;

    .el-header {
      padding: 0 auto;
      position: relative;
      background: #545c64;
      color: white;

      .left {
        // float: left;
        height: 100%;
        display: inline-flex;
        align-items: center;
        .imgtitle{
          height: 100%;
          background-image: url('../assets/35.jpg') ;
          background-size: 99.9%; 
        }

      }

      .toolbar {
        float: right;
        font-size: 12px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        right: 20px;

        .unloggen-btn :deep(.el-tooltip__trigger:focus-visible) {
          outline: unset;
        }
      }
    }

    .el-aside {
      background: #545c64;
      height: 100%;
    }

    .el-menu {
      border-right: none;
    }

    .el-main {
      padding: 5px;
      height: 100%;

    }
  }
}</style>