<template>
<div class="background">
  <div style="margin: 200px auto;background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
    <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
    <el-form :model="loginForm" :rules="rules" ref="loginForm" label-position="left" label-width="80px">
      <el-form-item label="登录名：" prop="loginName" >
        <el-input  prefix-icon="el-icon-user" v-model="loginForm.loginName"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input  prefix-icon="el-icon-lock" show-password v-model="loginForm.password"></el-input>
      </el-form-item>
      <el-form-item style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login('loginForm')">登录</el-button>
        <el-button type="warning" size="small" autocomplete="off"  @click="$router.push('/regis')">注册</el-button>
      </el-form-item>
    </el-form>

  </div>

</div>
</template>

<script>
import {setUserRoutes} from "@/router";

export default {
  name: "Login",
  data(){

    return{
      labelPosition: 'right',
      loginForm:{
        loginName: "",
        password:"",
      },
      rules:{
        loginName:[
          {required:true,message:'请填写用户名',trigger:'blur'}
        ],
        password:[
          {required:true,message:'请填写密码',trigger:'blur'}
        ]
      },
    }
  },
  methods: {
    login(formName){
      this.$refs[formName].validate((valid)=>{
        if(valid){
          this.request.post("/auth/login",this.loginForm).then(res=>{
            if(res.code === "200") {
              localStorage.setItem("user",JSON.stringify(res.data))

              // 设置动态路由
              setUserRoutes();
              this.$router.push("/")
              this.$message.success("登录成功")
            }else{
              this.$message.error(res.msg)
            }

          })
        }else{
          return false;
        }
      })

    },
  },
}
</script>

<style>
.background{
  height:100vh;
  background-image: linear-gradient(to bottom right, #FC466B,#3F5EFB);
  overflow: hidden;

}
</style>