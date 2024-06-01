//用户基本信息储存

import { defineStore } from "pinia";
import { ref } from "vue";
const useUserInfoStore = defineStore('userinfo', () => {

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
export default useUserInfoStore;
