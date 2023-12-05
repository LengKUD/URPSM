<template>
  <el-form :model="role">
    <el-form-item label="角色名称" :label-width="formLabelWidth">
      <el-input v-model="role.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="描述" :label-width="formLabelWidth">
      <el-input v-model="role.description" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "RoleInfo",
  props: {
    role: {},
    formLabelWidth: String,

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


</style>