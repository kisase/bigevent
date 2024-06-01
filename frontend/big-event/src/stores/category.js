//类别基本信息储存

import { defineStore } from "pinia";
import { ref } from "vue";
const categoryInfoStore = defineStore('categoryinfo', () => {

    const info = ref({})
    const setInfo = (newinfo) => {
        info.value = newinfo
    }
    const removeInfo = () => {
        info.value = {}
    }

    return { info, setInfo, removeInfo }
}, {
    persist: true
})
export default categoryInfoStore;
