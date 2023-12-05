<template>
<div>

  <div style="margin: 10px 0">
    <el-input v-model="s_originalFilename" placeholder="请输入搜索文件名" class="ml-5" style="width: 200px" suffix-icon="el-icon-files"></el-input>
    <el-input v-model="s_type" placeholder="请输入搜索文件类型" class="ml-5" style="width: 200px" suffix-icon="el-icon-c-scale-to-original"></el-input>
    <el-button type="primary" class="ml-5" icon="el-icon-search" @click="loadFiles">搜索</el-button>
    <el-button type="info" class="ml-5" icon="el-icon-refresh-left" @click="resetForm">重置</el-button>
  </div>
  <div style="margin: 10px 0">
    <el-upload style="display: inline-block" class="ml-5" :show-file-list="false" :on-success="uploadSuccess"
               action="http://localhost:9090/files/upload">
      <el-button type="success" icon="el-icon-upload2">上传文件</el-button>
    </el-upload>
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
    <el-table-column prop="originalFilename" label="文件名"  align="center"></el-table-column>
    <el-table-column prop="type" label="文件类型" align="center"></el-table-column>
    <el-table-column prop="size" label="文件大小" align="center"></el-table-column>

    <el-table-column  label="是否启用" key="scope" align="center">
      <template #default="scope">
<!--        <el-tooltip  :content="'点击关闭 tooltip 功能' + (scope.row.disable==='0')?'启用':'禁用'" placement="top">-->
        <el-tooltip  :content="(scope.row.disable===0)?'启用':'禁用'" placement="top">

          <!--        绑定数字要 用“:active-value="0"” 要加冒号-->
          <el-switch
                  v-model="scope.row.disable"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="0"
                  :inactive-value="1"
                  @change="changeDisable(scope.row)"
          ></el-switch>
        </el-tooltip>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="上传时间" width="140"></el-table-column>
    <el-table-column prop="option" label="操作"  fixed="right"  width="200">
      <template v-slot="scope">

        <el-button size="small" class="ml-5" type="primary" icon="el-icon-download" @click="download(scope.row.url)">下载</el-button>

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

</div>
</template>

<script>

export default {
  name: "FilesServer",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,

      s_originalFilename: "",
      s_type: "",
      disable: "0",

      formLabelWidth: "120px",
      multipleSelection: [],


    }
  },
  created() {
    this.loadFiles()
  },
  methods:{

    loadFiles(){

      this.request.get("/files/page?", {params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          s_originalFilename: this.s_originalFilename,
          s_type: this.s_type,
        }})
          .then(res=>{
            this.tableData = res.data.records;
            this.total = res.data.total;
          })

    },
    handleSizeChange(val){
      this.pageSize= val;
      this.loadFiles();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.loadFiles();
    },
    resetForm(){
      this.s_originalFilename = "";
      this.s_type = "";
    },

    deleteBatch(){
      let ids = this.multipleSelection.map(value => value.id)

      if(ids !=null && ids.length !=0){
        this.request.post("/files/del/batch",ids).then(res=>{
          if(res.code==='200' && res.data===true){
            this.$message.success("批量删除成功")
            this.loadFiles();
          }else{
            this.$message.error("批量删除失败," + res.msg)
          }
        })
      }
    },
    deleteRow(index ,rows){

      this.request.delete("/files/del/"+rows[index].id).then(res=>{
        if(res.code==='200' && res.data===true){
          this.$message.success("删除成功")
          this.dialogFormVisible = false;
          rows.splice(index, 1);

        }else{
          this.$message.error("删除失败," + res.msg)
        }
      })

    },

    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    download(url){
      window.open(url)
    },
    changeDisable(row){
      this.request.post("/files",row).then(res=>{
        if(res.code==='200' && res.data===true){
          this.$message.success("保存成功")
          this.loadFiles();
        }else{
          this.$message.error("保存失败,"+ + res.msg)
        }
      })

    },
    uploadSuccess(){
      this.loadFiles()
      this.$message.success("导入成功")
    },



  }
}
</script>

<style scoped>

</style>