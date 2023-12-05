<template>
  <el-form :model="menu">
    <el-form-item label="父级菜单" :label-width="formLabelWidth">
      <el-input v-model="menu.parents" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="菜单名称" :label-width="formLabelWidth">
      <el-input v-model="menu.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="路径" :label-width="formLabelWidth">
      <el-input v-model="menu.path" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="图标" :label-width="formLabelWidth">
<!--      <el-input v-model="menu.icon" autocomplete="off"></el-input>-->
      <el-select v-model="menu.icon"  placeholder="请选择">
        <el-option
            v-for="item in icon"
            :key="item.id"
            :label="item.name"
            :value="item.id.toString()">
          <span style="float: left"><i :class="item.value"></i></span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ item.name }}</span>
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="描述" :label-width="formLabelWidth">
      <el-input v-model="menu.description" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "MenuInfo",
  props: {
    menu: {},
    formLabelWidth: String,
    icon:[],
    },
  data() {
    return {

    };
  },

  methods: {

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