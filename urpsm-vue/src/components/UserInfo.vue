<template>
  <el-form :model="user">
    <el-upload
        class="avatar-uploader"
        action="http://localhost:9090/files/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
      <img v-if="user.avatar" :src="user.avatar" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-form-item label="登录名" :label-width="formLabelWidth">
      <el-input v-model="user.loginName" autocomplete="off" :disabled="LNdisabled"></el-input>
    </el-form-item>
    <el-form-item label="姓名" :label-width="formLabelWidth">
      <el-input v-model="user.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="昵称" :label-width="formLabelWidth">
      <el-input v-model="user.nickname" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="电话" :label-width="formLabelWidth">
      <el-input v-model="user.phone" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" :label-width="formLabelWidth">
      <el-input v-model="user.email" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="地址" :label-width="formLabelWidth">
      <el-input v-model="user.address" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="头像" :label-width="formLabelWidth">
      <el-input v-model="user.avatar" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "UserInfo",
  props: {
    user: {},
    formLabelWidth: String,
    LNdisabled: Boolean,



  },
  data() {
    return {

    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(file)
      this.user.avatar = file.response;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const LtSize = 10;

      if (!isJPG && !isPNG ) {
        this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
      }
      if (file.size / 1024 / 1024 > LtSize) {
        this.$message.error('上传头像图片大小不能超过 ' +LtSize+'MB!');
      }
      return isJPG && LtSize;
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 20px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>