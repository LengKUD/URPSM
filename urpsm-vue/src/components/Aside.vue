<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(48,58,86)"
             text-color="#fff"
             active-text-color="yellow"
             :collapse-transition="false"
             :collapse=isCollapse
             router
    ><!--             增加根据路由选择功能 “router”-->
      <div style="height: 60px;line-height: 60px;text-align: center;background-color: royalblue">
        <img src="../assets/logo.png" alt="logo图片" title="URP管理系统" style="width: 20px;position: relative;top: 5px;margin-right: 5px">
        <span v-if=!isCollapse  style="margin-right: 35px;" ><b style="color: white">URP管理系统</b></span>
      </div>
      <menu-tree :user-menu="this.userMenu"></menu-tree>

    </el-menu>

</template>

<script>
import MenuTree from "@/components/MenuTree";
export default {
  name: "Aside",
  components: {MenuTree},
  props:{
    isCollapse: false,
  },
  data(){
    return{
      userMenu:JSON.parse(localStorage.getItem("user")).menuList,
    }
  },



}
</script>

<style scoped>

</style>