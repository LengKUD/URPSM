<template>
  <el-container style="min-height: 100%">
    <el-aside :width="menuSideWidth" style="background-color: rgb(238, 241, 246);min-height:100%; box-shadow: 2px 0 6px rgba(0,21,41,.35)">
      <Aside :isCollapse="this.isCollapse"></Aside>
    </el-aside>
    <el-container>
      <el-header style=" border-bottom: 1px solid #ccc;">
      <Header :collapse-btn-class="this.collapseBtnClass" :collapse="this.collapse" :user="this.user"></Header>
      </el-header>
      <el-main>

<!--        当前页面子路由会在<router-view></router-view>中展示-->
        <router-view @refersUser="loadUser"></router-view>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";


export default {
  name: 'ManageView',
  data(){
    return{
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      menuSideWidth:'200px',
      user:{},

    }
  },
  created() {
    this.loadUser();
  },
  components:{
    Header,
    Aside,
  },
  methods:{
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.menuSideWidth = '64px';
        this.collapseBtnClass='el-icon-s-unfold'
      }else{
        this.menuSideWidth = '200px'
        this.collapseBtnClass='el-icon-s-fold'
      }
    },
    loadUser(){
      this.request.get("/user/current")
          .then(res=>{

            if (res.code==='200') {
              this.user = res.data;
            }
          })

    },

  }
}
</script>
