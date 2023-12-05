<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1px; font-size: 18px">
      <span :class="collapseBtnClass" style=" cursor: pointer" @click="collapse"></span>
      <el-breadcrumb style="display: inline-block; margin-left: 20px" separator-class="el-icon-arrow-right" class="ml-5" >
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>活动管理</el-breadcrumb-item>
        <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown @command="handleCommand">
      <img :src="user.avatar"
           style="width: 50px; border-radius: 50%;position: relative; top:10px;right: 10px">
        <span class="el-dropdown-link">{{ user.name }}
          <i class="el-icon-arrow-down" ></i>
        </span>

        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item  command="userInfo" icon="el-icon-share">用户信息</el-dropdown-item>
          <el-dropdown-item  command="changePwd" icon="el-icon-edit">修改密码</el-dropdown-item>
          <el-dropdown-item  command="logout" icon="el-icon-turn-off" divided>注销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
  </div>

</template>

<script>
import {resetUserRoutes} from "@/router";

export default {
  name: "Header",
  data(){
    return{
      // user:{},
    }
  },
  props:{
    collapseBtnClass: String,
    collapse: Function,
    user: Object,
  },
  created() {
    // this.user = JSON.parse(localStorage.getItem("user"))
  },
  methods:{
    handleCommand(command){
      switch (command) {
        case  "logout":
          localStorage.removeItem('user');
          resetUserRoutes()
          this.$router.push('/login');
          break;
        case "userInfo":
          // this.$router.push({
          //   name:'UserinfoEdit',
          //   params:{
          //     id: this.user.id}
          // });
          this.$router.push('/userinfoEdit');
        case "changePwd":
          // this.$router.push({
          //   name:'UserinfoEdit',
          //   params:{
          //     id: this.user.id}
          // });
          this.$router.push('/userinfoEdit');
          break;

      }

    },
  }
}
</script>

<style scoped>

</style>