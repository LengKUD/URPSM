<template>
<div class="background">
  <div style="margin: 150px auto;background-color: #fff;width: 500px;height: 500px;padding: 20px;border-radius: 10px">
    <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>用户注册</b></div>
    <el-form :model="regisForm" :rules="rules" ref="regisForm" label-position="right" label-width="100px">
      <el-form-item label="登录名：" prop="loginName" >
        <el-input  prefix-icon="el-icon-user" v-model="regisForm.loginName"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input  prefix-icon="el-icon-lock" show-password v-model="regisForm.password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码：" prop="checkPass">
        <el-input  prefix-icon="el-icon-lock" show-password v-model="regisForm.checkPass"></el-input>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email" >
        <el-input  prefix-icon="el-icon-folder" v-model="regisForm.email"></el-input>
      </el-form-item>
      <el-form-item style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="regis('regisForm')">注册</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
        <el-button type="info" size="small" autocomplete="off" @click="resetForm('regisForm')">重置</el-button>
      </el-form-item>
    </el-form>

  </div>

</div>
</template>

<script>
export default {
  name: "regis",
  data(){

    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.regisForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return{
      labelPosition: 'right',
      regisForm:{
        loginName: "",
        password:"",
        checkPass:"",
        email:"",
      },
      rules:{
        loginName:[
          {required:true,message:'请填写用户名',trigger:'blur'}
        ],
        password:[
          {required:true,message:'请填写密码',trigger:'blur'}
        ],
        checkPass:[
          {required:true,message:'请确认密码',trigger:'blur'},
          { validator: validatePass, trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    regis(formName){
      this.$refs[formName].validate((valid)=>{
        if(valid){

          this.request.post("/auth/regis",this.regisForm).then(res=>{
            if(res.code === "200" && res.data) {
              console.log(res.data)
              this.$router.push("/login")
              this.$message.success("注册成功，请登录")
            }else{
              this.$message.error(res.msg)
            }

          })

        }else{
          return false;
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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