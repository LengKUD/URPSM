<template>
<div>
  <div style="margin: 10px 0">
    <el-input v-model="s_name" placeholder="请输入搜索姓名" class="ml-5" style="width: 200px" suffix-icon="el-icon-s-custom"></el-input>
    <el-button type="primary" class="ml-5" icon="el-icon-search" @click="loadRole">搜索</el-button>
    <el-button type="info" class="ml-5" icon="el-icon-refresh-left" @click="resetForm">重置</el-button>
  </div>
  <div style="margin: 10px 0">
    <el-button class="ml-5" type="success" icon="el-icon-user" @click="addRole">新增</el-button>
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
  </div>
  <el-table :data="tableData" stripe :header-cell-style="{'text-align':'center'}" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"></el-table-column>
    <el-table-column prop="id" label="id" width="50" align="center"></el-table-column>
    <el-table-column prop="name" label="角色名称" align="center"></el-table-column>
    <el-table-column prop="description" label="描述" ></el-table-column>
    <el-table-column prop="createTime" label="创建日期" width="140"></el-table-column>
    <el-table-column prop="option" label="操作"  fixed="right"  width="300">
      <template v-slot="scope">
        <el-button size="small" class="ml-5" type="warning" icon="el-icon-share" @click.native.prevent="openDialog(scope.$index, scope.row)">菜单权限</el-button>
        <el-button size="small" class="ml-5" type="primary" icon="el-icon-edit" @click.native.prevent="editRole(scope.$index, scope.row)">编辑</el-button>
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
    <role-info :form-label-width="this.formLabelWidth" :role="this.role"></role-info>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveRole">确 定</el-button>
    </div>
  </el-dialog>

  <el-drawer
      title="分配菜单权限"
      :visible.sync="dialog"
      direction="rtl"
      custom-class="demo-drawer"
      ref="drawer"
  >
    <div>
      <el-form :model="role">
        <el-form-item label="角色名称：" :label-width="formLabelWidth">
          <el-input v-model="role.name" autocomplete="off" readonly></el-input>
        </el-form-item>
      </el-form>
    </div>

    <div style="text-align: center">
      <el-tree
          :data="menuList"
          show-checkbox
          node-key="id"
          ref="tree"
          default-expand-all
          :default-checked-keys="checkedkeys"
          :props="defaultProps">
      </el-tree>

    </div>


    <div style="text-align: center">
      <el-button @click="cancelDrawer">取 消</el-button>
      <el-button type="primary" @click="saveMenus" >确 定</el-button>
    </div>

  </el-drawer>
</div>
</template>
<script>
import RoleInfo from "@/components/RoleInfo";
export default {
  name: "Role",
  components: {RoleInfo},
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      s_name: "",
      dialogFormVisible: false,
      dialogName: "",
      role: {},
      formLabelWidth: "120px",
      multipleSelection: [],
      dialog: false,
      menuList:[],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      checkedkeys: [],

    }
  },
  created() {
    this.loadRole()
  },
  methods:{

    loadRole(){

      this.request.get("/role/page?", {params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          s_name: this.s_name,
        }})
          .then(res=>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })

    },
    handleSizeChange(val){

      this.pageSize= val;
      this.loadRole();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.loadRole();
    },
    resetForm(){
      this.s_name = "";
      this.loadRole();
    },
    addRole(){
      this.dialogName ="新增用户";
      this.dialogFormVisible = true;
      this.role= {};

    },
    editRole(index , row){
      this.dialogName ="编辑用户";
      this.dialogFormVisible = true;
      this.request.get("/role/"+ row.id).then(res=>{
        this.role = res.data;
      })


    },
    deleteBatch(){
      let ids = this.multipleSelection.map(value => value.id)

      if(ids !=null && ids.length !=0){
        this.request.post("/role/del/batch",ids).then(res=>{
          if(res.code==='200' && res.data===true){
            this.$message.success("批量删除成功")
            this.loadRole();


          }else{
            this.$message.error("批量删除失败," + res.msg)
          }
        })
      }
    },
    deleteRow(index ,rows){

      this.request.delete("/role/del/"+rows[index].id).then(res=>{
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
    saveRole(){
      this.request.post("/role",this.role).then(res=>{

        if(res.code==='200' && res.data===true){
          this.$message.success("保存成功")
          this.dialogFormVisible = false;
          this.loadRole();
        }else{
          this.$message.error("保存失败,"+ + res.msg)
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    openDialog(index , row){
      this.dialog = true;
      this.request.get("/role/"+ row.id).then(res=>{
        this.role = res.data;

        this.checkedkeys=(this.role.menus).split(',');


      })



      this.request.get("/menu")
          .then(res=>{
            this.menuList = res.data;
          })

    },
    saveMenus(){
      this.role.menus = this.$refs.tree.getCheckedKeys().toString();
      this.saveRole()
      this.dialog = false


    },
    cancelDrawer(){
      this.dialog = false
    },

  }
}
</script>

<style scoped>

</style>