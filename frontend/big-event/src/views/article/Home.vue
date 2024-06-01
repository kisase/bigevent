<script setup>
import { ref } from 'vue'

//获取文章分类内容
const categoryData = ref([]);
import { findCategoryService } from '@/api/categoryApi';
const findCategoryList = async () => {
    let result = await findCategoryService();
    categoryData.value = result.data;
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
import { articleAllListService } from '@/api/articleApi';
const articleListData = ref([]);
const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        title: SearchData.value.title ? SearchData.value.title : null,
        categoryId: SearchData.value.categoryId ? SearchData.value.categoryId : null
    }
    let result = await articleAllListService(params);
    total.value = result.data.total;
    articleListData.value = result.data.items;

    console.log(articleListData.value)
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

const handleClick = (id)=> {
    const url = `/article/${id}`;
    window.open(url, '_blank');
}


</script>
<template>
    <el-card>
        <el-scrollbar height="70vh">
            <div class="container">
                <div class="card" v-for="article in articleListData" :key="article.id">
                    <div class="card-image-container" @click="handleClick(article.id)">
                        <img :src="article.coverImg"  alt="Article Image" />
                        <p class="image-title">{{ article.title }}</p>
                    </div>
                </div>
        
            </div>
        </el-scrollbar>
        <!-- 分页框架 -->
        <div class="demo-pagination-block" style="padding-top:10px ;">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[10, 20, 30, 40]"
                background layout="->, jumper,prev, pager, next, total, sizes" :total="total" @size-change="onSizeChange"
                @current-change="onCurrentChange" />
        </div>
    </el-card>
</template>
<style scoped lang="scss">
.container {
    display: flex;
    flex-wrap: wrap; // 自动换行
    overflow: auto; // 添加滚动条
}

.card {
    width: 300px; // 设置卡片宽度
    border: 1px solid #ccc;
    border-radius: 8px;
    margin: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden; // 确保内部元素不超出边界

    .card-image-container {
        position: relative;
        width: 100%;
        cursor: pointer; // 鼠标指针变为手形

        img {
            display: block; // 确保图片为块级元素
            width: 100%;
            height: auto; // 保持图片比例
        }

        .image-title {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 10px;
            margin: 0;
            opacity: 1; // 始终显示文字
            display: flex;
            justify-content: center;
            align-items: center;
        }
    }
}



</style>