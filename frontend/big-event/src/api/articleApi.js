//导入request.js 请求工具（拦截器）
import request from "@/utils/request.js"

//文章列表查询
export const articleListService = (params)=>{
    return request.get("/article",{params:params});
}
//文章发布列表查询
export const articleAllListService = (params)=>{
    return request.get("/article/published",{params:params});
}

//添加文章
export const articleAddService = (articleData)=>{
    return request.post("/article",articleData);
}
//编辑文章
export const articleUpdateService =(articleDate)=>{
    return request.post('/article/update',articleDate);
}
// 删除文章
export const articleDeleteService = (id)=>{
    return request.put("/article?id="+id)
}
//文章获取
export const articleFindByIdService = (id)=>{
    return request.post("/article/findById?id="+id)
}