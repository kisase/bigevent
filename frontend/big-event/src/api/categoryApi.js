//导入request.js 请求工具（拦截器）
import request from "@/utils/request.js"

//获取文章分类
export const findCategoryService =()=>{
    return request.get('/category')
}

//添加文章分类
export const addCategoryService =(categoryData)=>{
    return request.post('/category/add',categoryData)
}
//编辑文章分类
export const updateCategoryService =(categoryData)=>{
    return request.post('/category/update',categoryData)
}

//删除文章分类
export const deleteCategoryService =(id)=>{
    return request.put('/category/delete?id='+id)
}