<template>
</template>
  <script>
    export default {
      inheritAttrs: false,
      components: {},
      props: [],
      data() {
        return {
          formData: {
            mobile: '',
            field101: undefined,
          },
          rules: {
            mobile: [{
              required: true,
              message: '请输入手机号',
              trigger: 'blur'
            }, {
              pattern: /^1(3|4|5|7|8|9)\d{9}$/,
              message: '手机号格式错误',
              trigger: 'blur'
            }],
            field101: [{
              required: true,
              message: '请输入单行文本',
              trigger: 'blur'
            }],
          },
        }
      },
      computed: {},
      watch: {},
      created() {},
      mounted() {},
      methods: {
        onOpen() {},
        onClose() {
          this.$refs['elForm'].resetFields()
        },
        close() {
          this.$emit('update:visible', false)
        },
        handleConfirm() {
          this.$refs['elForm'].validate(valid => {
            if (!valid) return
            this.close()
          })
        },
      }
    }

  </script>
  <style>
  </style>

</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'ManageView',
  data(){

    return{
      tableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      menuSideWidth:'200px',
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


    }
  },
  created() {
    this.loadUser()
  },
  components:{
    Header,
    Aside,
  },
  methods:{
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.menuSideWidth = '64px';
        this.collapseBtnClass='el-icon-s-unfold'
      }else{
        this.menuSideWidth = '200px'
        this.collapseBtnClass='el-icon-s-fold'
      }

    },
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
            this.tableData = res.records;
            this.total = res.total;
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
      this.user= {};

    },
    editUser(index ,row){
      this.dialogName ="编辑用户";
      this.dialogFormVisible = true;
      this.request.get("/user/"+ row.id).then(res=>{
        this.user = res;
      })


    },
    deleteBatch(){
      let ids = this.multipleSelection.map(value => value.id)

      if(ids !=null && ids.length !=0){
        this.request.post("/user/del/batch",ids).then(res=>{
          if(res){
            this.$message.success("批量删除成功")
            this.loadUser();


          }else{
            this.$message.error("批量删除失败")
          }
        })
      }
    },
    deleteRow(index ,rows){

      this.request.delete("/user/del/"+rows[index].id).then(res=>{
        if(res){
          this.$message.success("删除成功")
          this.dialogFormVisible = false;
          rows.splice(index, 1);
          // this.loadUser();
        }else{
          this.$message.error("删除失败")
        }
      })

    },
    saveUser(){
      this.request.post("/user",this.user).then(res=>{

        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false;
          this.loadUser();
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
  }
}
</script>
