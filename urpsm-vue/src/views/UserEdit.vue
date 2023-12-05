<template>
<div>
  <el-card >
    <div slot="header">
      <span>用户信息</span>
    </div>
    <user-info :user="this.user" :form-label-width="this.formLabelWidth" :l-ndisabled="true"></user-info>
    <div style="text-align: center" >
      <el-button @click="$router.go(-1)">取 消</el-button>
      <el-button type="primary" @click="saveUser">保 存</el-button>
    </div>
  </el-card >
</div>
</template>

<script>
import UserInfo from "@/components/UserInfo";
export default {
  name: "UserEdit",
  components: {UserInfo},
  data(){
    return{

      user: {},
      formLabelWidth: "120px",


    }
  },
  created() {
    this.loadUser()
  },
  methods:{

    loadUser(){

      // this.request.get("/user/"+ this.$route.params.id)
      this.request.get("/user/current")
          .then(res=>{

            if (res.code==='200') {
              this.user = res.data;
            }
          })

    },

    saveUser(){
      this.request.post("/user",this.user).then(res=>{

        if(res.code==='200' && res.data===true){
          this.$message.success("保存成功")
          this.$router.go(-1)
          //触发父级更新User方法
          this.$emit("refersUser");

        }else{
          this.$message.error("保存失败," + res.msg)
        }
      })
    },

  }
}
</script>

<style scoped>

</style>