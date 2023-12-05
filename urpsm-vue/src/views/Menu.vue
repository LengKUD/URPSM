<template>
<div>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card>

        <div class="custom-tree-container">

          <div>

            <div style="display: flex;"><span style="line-height: 30px">菜单列表</span>
              <div>
                <el-tooltip class="item" effect="dark" content="添加主菜单" placement="top">
                <el-button class="ml-5" icon="el-icon-plus" circle  type="primary" size="mini"></el-button>
                </el-tooltip>
              </div>
            </div>
            <el-tree
                :data="menuList"
                :show-checkbox="isshowcheckbox"
                node-key="id"
                default-expand-all
                :expand-on-click-node="false"
                :props="defaultProps"
                @node-click="handleNodeClick"
            >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span><i :class="data.iconType"></i>{{ node.label }}</span>
        <span></span>
        <span>
          <el-tooltip class="item" effect="dark" content="添加子菜单" placement="top">
          <el-button
              icon="el-icon-plus" circle
              type="primary"
              size="mini"
              @click="() => append(data)">
          </el-button>
          </el-tooltip>
           <el-tooltip class="item" effect="dark" content="删除菜单" placement="top">
          <el-button
              icon="el-icon-delete" circle
              type="danger"
              size="mini"
              @click="() => remove(node, data)">
          </el-button>
           </el-tooltip>
        </span>
      </span>
            </el-tree>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card v-if="editCard">
        <div><menu-info :menu="this.menu" :icon="this.icon" :form-label-width="this.formLabelWidth"></menu-info></div>
        <div style="text-align: center" >
          <el-button type="primary" @click="update">更 新</el-button>
        </div>
      </el-card>
      <el-dialog title="新增菜单" :visible.sync="addCard">
        <div><menu-info :menu="this.newMenu" :icon="this.icon" :form-label-width="this.formLabelWidth"></menu-info></div>
        <div style="text-align: center" >
          <el-button type="primary" @click="add">新 增</el-button>
        </div>
      </el-dialog>
    </el-col>
  </el-row>
</div>
</template>

<script>
import MenuInfo from "@/components/MenuInfo";

let id = 1000;

export default {
  name:"Menu",
  components:{MenuInfo},
  data() {
    return {
      menuList : [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      menu: {},
      newMenu: {},
      formLabelWidth: "120px",
      isshowcheckbox: false,
      addOrUpdate:"",
      editCard: false,
      addCard: false,
      icon:[],
    }
  },

  created() {
    this.loadMenu()

  },
  methods: {
    loadMenu(){
      this.request.get("/menu")
          .then(res=>{
            this.menuList = res.data;
          })

    },

    append(data) {
      this.addCard=true;
      this.newMenu={}
      this.newMenu.parents= data.name
      this.newMenu.pid= data.id

    },

    remove(node, data) {
      this.request.delete("/menu/del/" + data.id).then(res=>{
        if(res.code==='200' && res.data){
          this.$message.success("更新成功")
        }else{
          this.$message.error("更新失败："  + res.msg +":" + res.code)
        }
      })

      const parent = node.parent;
      const children = parent.data.children || parent.data;
      const index = children.findIndex(d => d.id === data.id);
      children.splice(index, 1);
    },

    handleNodeClick(data,node){
      this.editCard=  true;
      this.menu = data;
      this.menu.parents = node.parent.data.name;

      this.request.get("/dict").then(res=>{
        if(res.code==='200'){
          this.icon=res.data
        }
      })
    },
    update(){
      this.saveMenu(this.menu)
    },
    add(){
      this.saveMenu(this.newMenu)
      this.addCard=false;

      // this.loadMenu()
      if (!this.menu.children) {
        this.$set(this.menu, 'children', []);
      }
      this.menu.children.push(this.newMenu);
    },

    saveMenu(menu){
      this.request.post("/menu",menu).then(res=>{
        if(res.code==="200"){
          menu.id=res.data;
          this.$message.success("更新成功")
        }else{
          this.$message.error("更新失败："  + res.msg +":" + res.code)
        }

      })

    },

  }
};

</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 20px;
}

</style>