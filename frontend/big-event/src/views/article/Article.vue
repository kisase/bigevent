<script setup>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit } from '@element-plus/icons-vue'
//获取请求头token值
import { userTokenStore } from '@/stores/token.js'
const userToken = userTokenStore();
//获取文章分类内容
const categoryData = ref([]);
import { findCategoryService } from '@/api/categoryApi';
const findCategoryList = async () => {
  let result = await findCategoryService();
  categoryData.value = result.data
}
findCategoryList();

//搜索数据模型
const SearchData = ref({
  title: '',
  categoryId: ''
})
//分页数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  articleList();
}
//当前页码数发生了变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  articleList();
}
//获取文章列表内容
import { articleListService, articleAddService, articleUpdateService, articleDeleteService } from '@/api/articleApi';
const articleListData = ref([]);
const articleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    title: SearchData.value.title ? SearchData.value.title : null,
    categoryId: SearchData.value.categoryId ? SearchData.value.categoryId : null
  }
  let result = await articleListService(params);
  total.value = result.data.total;
  articleListData.value = result.data.items;
  //分类id转换名称
  for (let i = 0; i < articleListData.value.length; i++) {
    let article = articleListData.value[i];
    for (let j = 0; j < categoryData.value.length; j++) {
      if (article.categoryId == categoryData.value[j].id) {
        article.categoryName = categoryData.value[j].categoryName;
      }
    }
  }

}
articleList()

//搜索按钮函数
const SearchBtn = () => {

  articleList();
}
//重置按钮函数
const removeSearch = ()=>{
  SearchData.value={
    title: '',
  categoryId: ''
  }
}

//弹窗展示
const dialog = ref(false)
//弹窗标题
const title = ref('')
//添加表单数据模型
const articleModel = ref({
  title: '',
  categoryId: '',
  coverImg: '',
  content: '',
  state: ''
})
const rules = {
  title: [
    { required: true, message: "请输入标题", trigger: "blur" },
    { min: 5,max:20, message: "长度为5~20位非空字符！", trigger: "blur" }
  ],
  categoryId: [
    { required: true, message: "请选择类别", trigger: "blur" },
    
  ],
  coverImg: [
    { required: true, message: "请添加封面", trigger: "blur" },
  ],
  content: [
    { required: true, message: "请输入内容", trigger: "blur" },
  ]
}
//清空数据模型
const clearData = () => {
  articleModel.value = {
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
  }
}


//图片上传成功回调函数
const uploadSuccess = (result) => {
  articleModel.value.coverImg = result.data;

}
const articleForm = ref(null); // 表单的 ref
//添加文章
const addArticle = async (clickstate) => {
  const isValid = await validateArticle();
  if (!isValid) {
    return;
  }
  articleModel.value.state = clickstate;
  //调用接口
  let result = await articleAddService(articleModel.value);
  ElMessage({
    type: 'success',
    message: result.message ? result.message : '添加成功',
  })
  //让抽屉消失
  dialog.value = false;
  //刷新列表
  articleList();
}

//展示编辑弹窗分类
const showDialog = (row) => {
  dialog.value = true; title.value = '编辑分类';
  //获取值
  articleModel.value.title = row.title;
  articleModel.value.categoryId = row.categoryId;
  articleModel.value.coverImg = row.coverImg;
  articleModel.value.content = row.content;
  //添加变量，以id来编辑
  articleModel.value.id = row.id
}
//编辑文章
const updataArticle = async (clickstate) => {
  const isValid = await validateArticle();
  if (!isValid) {
    return;
  }
  articleModel.value.state = clickstate;
  let result = await articleUpdateService(articleModel.value);
  ElMessage({
    type: 'success',
    message: result.message ? result.message : '编辑成功',
  })
  //刷新列表
  articleList();
  //关闭弹窗
  dialog.value = false;
}

//删除文章
const deleteArticle = async (row) => {
  ElMessageBox.confirm(
    '你确定要删除吗？',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
      let result = await articleDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: result.message ? result.message : '删除成功',
      })
      //刷新列表
      articleList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '你取消了删除',
      })
    })
}

const validateArticle = async () => {
  try {
    await articleForm.value.validate(); // 假设你在模板中使用了 ref="articleForm" 来引用表单组件
    return true;
  } catch (error) {
    ElMessage.error('表单验证失败');
    return false;
  }
}
</script>


<template>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>文章管理</span>
        <el-button type="primary" style="float:right;" @click="dialog = true, title='添加文章', clearData()">添加文章</el-button>
      </div>
    </template>
    <!-- 搜索框架 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="文章标题">
        <el-input placeholder="请输入你想要的文章" v-model="SearchData.title" />
      </el-form-item>
      <el-form-item label="文章类别">
        <el-select v-model="SearchData.categoryName" value-key="id" placeholder="请选择文章类别" style="width: 150px">
          <el-option v-for="item in categoryData" :key="item.id" :label="item.categoryName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="SearchBtn">搜索</el-button>
        <el-button type="info" plain @click="removeSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表单框架 -->
    <el-table :data="articleListData" border height="340" style="width: 100%" show-overflow-tooltip>
      <el-table-column label="序号" type="index" width="80" />
      <el-table-column label="文章标题" prop="title" />
      <el-table-column label="类别" prop="categoryName" width="100" />
      <el-table-column label="内容" prop="content"  />
      <el-table-column label="封面"  width="140" > 
        <template #default="{ row }"> 
          <img :src="row.coverImg" style="height: 70px;"/>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" prop="updateTime" width="165" />
      <el-table-column label="状态" prop="state" width="80" />
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="{ row }">
          <el-button type="primary" :icon="Edit" circle plain @click="showDialog(row)" />
          <el-button type="danger" :icon="Delete" circle plain @click="deleteArticle(row)" />
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页框架 -->
    <div class="demo-pagination-block" style="padding-top:10px ;">
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[10, 20, 30, 40]" background
        layout="->, jumper,prev, pager, next, total, sizes" :total="total" @size-change="onSizeChange"
        @current-change="onCurrentChange" />
    </div>
  </el-card>
  <!-- 弹窗框架 -->
  <el-drawer v-model="dialog" :title=title direction="rtl" size="90%">

    <el-form :model="articleModel" label-width="100px" :rules="rules" ref="articleForm">
      <el-form-item label="文章标题" prop="title">
        <el-input v-model="articleModel.title" autocomplete="off" />
      </el-form-item>
      <el-form-item label="文章分类" prop="categoryId">
        <el-select v-model="articleModel.categoryId" value-key="id" placeholder="请选择文章类别" style="width: 150px">
          <el-option v-for="item in categoryData" :key="item.id" :label="item.categoryName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="文章封面" prop="coverImg">
        <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false" :on-success="uploadSuccess"
          :headers="{ Authorization: userToken.token }" :auto-upload="true">
          <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="文章内容" prop="content">
        <div class="editor">
          <quill-editor theme="snow" v-model:content="articleModel.content" contentType="html" />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="title == '添加文章' ? addArticle('发布') : updataArticle('发布')">发布</el-button>
        <el-button type="info" @click="title == '添加文章' ? addArticle('草稿') : updataArticle('草稿')">草稿</el-button>
      </el-form-item>
    </el-form>



  </el-drawer>
</template>

<style lang="scss" scoped>
.el-card {
  height: 99%;
}

.avatar-uploader {
  .avatar {
    width: 120px;
    height: 120px;
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
    width: 120px;
    height: 120px;
    text-align: center;
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
  }
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 400px;
  }
}
</style>