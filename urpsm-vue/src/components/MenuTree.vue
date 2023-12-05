<template>
  <div id="user_menu_tree">
    <div v-for="item in userMenu" :key="item.id">
      <el-menu-item v-if="item.children.length ==0" :index="item.path">
        <template slot="title"><i :class="item.iconType"></i>
          <span slot="title">{{ item.name }}</span></template>
      </el-menu-item>
      <el-submenu v-else :index="item.name">
        <template slot="title"><i :class="item.iconType"></i>
          <span slot="title">{{ item.name }}</span>
        </template>
        <menu-tree v-if="item.children && item.children.length >0"
                   :key="item.children.id"
                   :user-menu = item.children></menu-tree>
      </el-submenu>


    </div>

  </div>
</template>

<script>
export default {
  name: "MenuTree",
  props: {
    // 传入数据
    userMenu: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data(){
    return{

    }
  },
  created() {
    // let user = JSON.parse(localStorage.getItem("user"));
    // console.log("=====user.menuList")
    // console.log(user.menuList)

  },
  mounted() {
    // console.log("=====this.userMenu")
    // console.log(this.userMenu)
  },
  methods:{

  }

}
</script>

<style scoped>

</style>