//导入request.js 请求工具（拦截器）
import request from "@/utils/request.js"

//提供注册请求方法函数
export const userRegisterService = (registerData) => {
    //URLSearchParams 接口定义了一些实用的方法来处理 URL 的查询字符串。
    const params = new URLSearchParams()
    for (let key in registerData) {
        //URLSearchParams.append()插入一个指定的键/值对作为新的查询参数。
        params.append(key, registerData[key]);
    }
    return request.post('/user/register', params)
}
//提供登录接口
export const userLoginService = (registerData) => {
    const params = new URLSearchParams()
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    return request.post('/user/login', params)
}

//获取用户详情信息
export const userInfoService = () => {
    return request.get('/user/userInfo')
}

//修改个人信息
export const updataUserinfoService = (userData) => {
    return request.put('/user/update', userData)
}

//修改头像路径
export const userupdataAvatarService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl)
    return request.patch('/user/updateAvatar', params)
}
//修改密码
export const userupdataPasswordService = (pwdData) => {
    const params = JSON.stringify(pwdData)
    return request.patch('/user/updatePwd', params, {
        headers: {'Content-Type': 'application/json'}
    })
}