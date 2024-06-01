<script setup>
import {ref} from 'vue'
import { useRoute } from 'vue-router';
import {articleFindByIdService} from '@/api/articleApi.js'
const articleListData =ref({})
const route = useRoute();
const ArticleContent = async()=>{
   const result= await articleFindByIdService(route.params.id);
    console.log(result.data)
    articleListData.value=result.data
}
ArticleContent()

</script>

<template>
   <div class="center" v-if="articleListData">
      <h1 align="center">{{ articleListData.title }}</h1>
      <div v-html="articleListData.content"></div>
    </div>
    <p v-else>Loading article...</p>
</template>
<style scoped>
.center{
    margin: 0 100px;
    padding: 0 30px ;
}

</style>