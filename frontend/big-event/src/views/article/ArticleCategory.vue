<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit } from '@element-plus/icons-vue'
//获取文章分类内容
const tableData = ref([]);
import { findCategoryService, addCategoryService, updateCategoryService, deleteCategoryService } from '@/api/categoryApi';
const findCategoryList = async () => {
  let result = await findCategoryService();
  tableData.value = result.data
}
findCategoryList();

//添加分类
const dialogVisible = ref(false)
const categoryModel = ref({
  categoryName: '',
  categoryAlias: '',
})
//定义表单校验规则
const rules = {
  categoryName: [
    { required: true, message: "请输入分类名称", trigger: "blur" },
  ],
  categoryAlias: [
    { required: true, message: "请输入分类别名", trigger: "blur" },
  ]
}
//清空数据模型
const clearData = () => {
  categoryModel.value = {
    categoryName: '',
    categoryAlias: '',
  }
}
const articleCategoryForm = ref(null)

//提交添加分类
const addCategory = async () => {
  const isValid = await validateArticleCategory();
  if (!isValid) {
    return;
  }
  let result = await addCategoryService(categoryModel.value);
  ElMessage({
    type: 'success',
    message: result.message ? result.message : '添加成功',
  })
  //刷新列表
  findCategoryList();
  //关闭弹窗
  dialogVisible.value = false;
}

const title = ref('')
//展示编辑弹窗分类
const showDialog = (row) => {
  dialogVisible.value = true; title.value = '编辑分类';
  //获取值
  categoryModel.value.categoryName = row.categoryName
  categoryModel.value.categoryAlias = row.categoryAlias
  //添加变量，以id来编辑
  categoryModel.value.id = row.id
}

//编辑分类
const updataCategory = async () => {
  const isValid = await validateArticleCategory();
  if (!isValid) {
    return;
  }
  let result = await updateCategoryService(categoryModel.value);
  ElMessage({
    type: 'success',
    message: result.message ? result.message : '编辑成功',
  })
  //刷新列表
  findCategoryList();
  //关闭弹窗
  dialogVisible.value = false;
}
//删除分类
const deleteList = async (row) => {
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
      let result = await deleteCategoryService(row.id);
      ElMessage({
        type: 'success',
        message: result.message ? result.message : '删除成功',
      })
      //刷新列表
      findCategoryList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '你取消了删除',
      })
    })

}

const validateArticleCategory = async () => {
  try {
    await articleCategoryForm.value.validate(); // 假设你在模板中使用了 ref="articleForm" 来引用表单组件
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
        <span>文章分类</span>
        <el-button type="primary" style="float:right;"
          @click="dialogVisible = true, title = '添加分类', clearData()">添加分类</el-button>
      </div>
    </template>
    <el-table :data="tableData" height="400" style="width: 100%">
      <el-table-column label="序号" type="index" width="120" />
      <el-table-column label="分类名称" prop="categoryName" />
      <el-table-column label="分类别名" prop="categoryAlias" />
      <el-table-column label="操作" fixed="right" width="100">
        <template #default="{row}">
          <el-button type="primary" :icon="Edit" circle plain @click="showDialog(row)" />
          <el-button type="danger" :icon="Delete" circle plain @click="deleteList(row)" />
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>


    <!-- 分类添加弹窗 -->
    <el-dialog v-model="dialogVisible" :title=title width="500">
      <el-form ref="articleCategoryForm" :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px;">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryModel.categoryName" minlenght="1" maxlenght="10" />
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input v-model="categoryModel.categoryAlias" minlenght="1" maxlenght="15" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="title == '添加分类' ? addCategory() : updataCategory()">确认</el-button>
        </div>
      </template>
    </el-dialog>




  </el-card>
</template>

<style lang="scss" scoped>
.el-card {
  height: 99%;
}
</style>