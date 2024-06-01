//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInformationVue from'@/views/user/UserInformation.vue'
import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleVue from '@/views/article/Article.vue'
import UserResetPwdVue from '@/views/user/UserResetPwd.vue'
import HomeVue from '@/views/article/Home.vue'
import ArticleDetail from '@/views/article/ArticleDetail.vue'
//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/',
        component: LayoutVue,
        //重定向
        redirect:'/article/manage',
        children:[
            {path:'/article/home',component:HomeVue},
            {path:'/article/category',component:ArticleCategoryVue},
            {path:'/article/manage',component:ArticleVue},
            {path:'/user/avatar',component:UserAvatarVue},
            {path:'/user/info',component:UserInformationVue},
            {path:'/user/resetPwd',component:UserResetPwdVue},
        ]


    },
    {
        path: '/article/:id',
        name: 'ArticleDetail',
        component: ArticleDetail,
        props: true
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes:routes
})

export default router
