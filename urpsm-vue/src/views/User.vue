<template>
<div>

  <div style="margin: 10px 0">
    <el-input v-model="s_username" placeholder="请输入搜索姓名" class="ml-5" style="width: 200px" suffix-icon="el-icon-s-custom"></el-input>
    <el-input v-model="s_nickname" placeholder="请输入搜索昵称" class="ml-5" style="width: 200px" suffix-icon="el-icon-user"></el-input>
    <el-input v-model="s_address" placeholder="请输入搜索地址" class="ml-5" style="width: 200px" suffix-icon="el-icon-office-building"></el-input>
    <el-button type="primary" class="ml-5" icon="el-icon-search" @click="loadUser">搜索</el-button>
    <el-button type="info" class="ml-5" icon="el-icon-refresh-left" @click="resetForm">重置</el-button>
  </div>
  <div style="margin: 10px 0">
    <el-button class="ml-5" type="success" icon="el-icon-user" @click="addUser">新增</el-button>
    <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        title="确定删除选中内容吗？"
        @confirm ="deleteBatch">
      <el-button slot="reference"  type="danger" icon="el-icon-delete" @click=" show => { if(this.multipleSelection.length===0) this.$message.warning('没有选中用户')}">删除</el-button>
    </el-popconfirm>
    <el-upload style="display: inline-block" class="ml-5" :show-file-list="false" :accept="'.xlsx'" :on-success="impSuccess"
        action="http://localhost:9090/user/imp">
      <el-button type="info" icon="el-icon-document-add">导入</el-button>
    </el-upload>
    <el-button class="ml-5" type="info" icon="el-icon-document-copy" @click="exp">导出</el-button>
  </div>
  <el-table :data="tableData" stripe :header-cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"></el-table-column>
    <el-table-column prop="id" label="id" width="50" align="center"></el-table-column>
    <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
    <el-table-column prop="nickname" label="昵称" ></el-table-column>
    <el-table-column prop="loginName" label="登录名" ></el-table-column>
    <el-table-column prop="phone" label="电话" ></el-table-column>
    <el-table-column prop="email" label="邮箱" ></el-table-column>
    <el-table-column prop="address" label="地址" ></el-table-column>
    <el-table-column prop="createTime" label="登记日期" width="140"></el-table-column>
    <el-table-column prop="option" label="操作"  fixed="right"  width="300">
      <template v-slot="scope">
        <el-button size="small" class="ml-5" type="warning" icon="el-icon-share" @click.native.prevent="setRoles(scope.$index, scope.row)">分配角色</el-button>
        <el-button size="small" class="ml-5" type="primary" icon="el-icon-edit" @click.native.prevent="editUser(scope.$index, scope.row)">编辑</el-button>
        <el-popconfirm
            size="small" class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='取消'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm ="deleteRow(scope.$index, tableData)"
        >
          <el-button size="small" slot="reference"  type="danger" icon="el-icon-remove-outline" >删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div class="block" style="padding: 10px 0;">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20,50,100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total=total>
    </el-pagination>
  </div>


  <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
    <user-info :user="this.user" :form-label-width="this.formLabelWidth" :l-ndisabled="this.lndisabled"></user-info>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveUser">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="选择角色" :visible.sync="rolesDialog">
    <el-transfer v-model="userRoles"
                 :titles="['角色列表', '已有角色']"
                 :data="roles"
                 :props="{
                          key: 'id',
                          label: 'name'
                        }"
    ></el-transfer>
    <div slot="footer" class="dialog-footer">
      <el-button @click="rolesDialog = false">取 消</el-button>
      <el-button type="primary" @click="saveRoles">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
import UserInfo from "@/components/UserInfo";
export default {
  name: "User",
  components: {UserInfo},
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      s_username: "",
      s_nickname: "",
      s_address: "",
      dialogFormVisible: false,
      dialogName: "",
      user: {},
      formLabelWidth: "120px",
      multipleSelection: [],
      lndisabled: "",
      rolesDialog: false,
      roles: [],
      userRoles:[],

    }
  },
  created() {
    this.loadUser()
  },
  methods:{

    loadUser(){

      // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize +"&s_username=" +this.s_username)
      //     .then(res => res.json()).then(res => {
      //               this.tableData = res.records;
      //               this.total = res.total;
      // })
      this.request.get("/user/page?", {params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          s_username: this.s_username,
          s_nickname: this.s_nickname,
          s_address: this.s_address,
        }})
          .then(res=>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })

    },
    handleSizeChange(val){

      this.pageSize= val;
      this.loadUser();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.loadUser();
    },
    resetForm(){
      this.s_username = "";
      this.s_nickname = "";
      this.s_address = "";
    },
    addUser(){
      this.dialogName ="新增用户";
      this.dialogFormVisible = true;
      this.lndisabled = false;
      this.user= {};

    },
    editUser(index ,row){
      this.dialogName ="编辑用户";
      this.dialogFormVisible = true;
      this.lndisabled = true;
      this.request.get("/user/"+ row.id).then(res=>{
        this.user = res.data;
      })



    },
    deleteBatch(){
      let ids = this.multipleSelection.map(value => value.id)

      if(ids !=null && ids.length !=0){
        this.request.post("/user/del/batch",ids).then(res=>{
          if(res.code==='200' && res.data===true){
            this.$message.success("批量删除成功")
            this.loadUser();


          }else{
            this.$message.error("批量删除失败," + res.msg)
          }
        })
      }
    },
    deleteRow(index ,rows){

      this.request.delete("/user/del/"+rows[index].id).then(res=>{
        if(res.code==='200' && res.data===true){
          this.$message.success("删除成功")
          this.dialogFormVisible = false;
          rows.splice(index, 1);
          // this.loadUser();
        }else{
          this.$message.error("删除失败," + res.msg)
        }
      })

    },
    saveUser(){
      this.request.post("/user",this.user).then(res=>{

        if(res.code==='200' && res.data===true){
          this.$message.success("保存成功")
          this.dialogFormVisible = false;
          this.loadUser();
        }else{
          this.$message.error("保存失败,"+ + res.msg)
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    exp(){
      window.open("http://localhost:9090/user/exp")
    },
    impSuccess(){
      this.loadUser()
      this.$message.success("导入成功")
    },

    setRoles(index,row){

      this.request.get("/role").then(res=>{
        if(res.code==='200'){
          this.roles = res.data;
        }
      })


      this.request.get("/user/"+ row.id).then(res=>{

        if (res.code==='200'){
          this.user = res.data;
          this.userRoles = (this.user.roles == null)?[]: this.user.roles.split(',');
          this.userRoles = this.userRoles.map(Number)
          this.rolesDialog= true;
        }else{
          this.$message(res.code + res.message)
        }

      });







    },
    saveRoles(){
      this.user.roles = this.userRoles.toString();
      this.saveUser();
      this.userRoles = [];
      this.rolesDialog = false

    },

  }
}
</script>

<style scoped>

</style>