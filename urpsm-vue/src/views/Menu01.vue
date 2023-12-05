<template>
  <div id="children_tree">
    <!-- 将数据正常地循环展示 - 组件传入数据数组 parentTreeData -->
    <div v-for="item in parentTreeData" :key="item.category_id">
      <div class="row bg_row">
        <!-- 数据的左边部分 - showHidden点击方法是控制节点是否展开 -->
        <div class="col" @click="showHidden(item)">
          <i
              v-if="
              item.son &&
              item.son instanceof Array &&
              item.son.length > 0 &&
              !findTrue(item.category_id)
            "
              class="material-icons draicon"
          >arrow_right</i
          >
          <i
              v-else-if="
              item.son &&
              item.son instanceof Array &&
              item.son.length > 0 &&
              findTrue(item.category_id)
            "
              class="material-icons draicon"
          >arrow_drop_down</i
          >
          <i
              v-else
              class="material-icons draicon"
              style="padding-left: 38px"
          ></i>
          {{ item.name }}
        </div>
        <!-- 数据的右边放功能按钮的部分 - 接收传入的功能数组 btnOperates -->
        <div :class="`col-${5}`" style="text-align: right">
          <q-btn
              class="on-left"
              v-for="(btn, index) in btnOperates"
              :key="`btn_fun_${index}`"
              :label="btn.label || '按钮'"
              :color="btn.color || 'primary'"
              :outline="btn.outline || false"
              :size="btn.size || 'md'"
              @click="btn.fun(item)"
              unelevated
          />
        </div>
      </div>
      <!-- 调用自己 -->
      <children
          v-if="item.son && findTrue(item.category_id)"
          :key="item.category_id + 10000"
          :parent-tree-data="item.son"
          :tree-data="treeData"
          :btn-operates="btnOperates"
          style="margin-left: 15px"
      ></children>
    </div>
  </div>



</template>

<script>

export default {
  name: 'children',
  props: {
    // 传入数据
    parentTreeData: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 功能按钮
    btnOperates: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      // 用于存储是否展开显示的层级
      showArr: [],
      parentTreeData: [
        {
          category_id: 100,
          name: '父级一',
          parent_id: 0,
          son: [
            {
              category_id: 103,
              name: '子级一',
              parent_id: 100,
              son: [
                {
                  category_id: 106,
                  name: '孙级一',
                  parent_id: 103,
                  son: [
                    {
                      category_id: 107,
                      name: '曾孙级一',
                      parent_id: 106
                    }
                  ]
                }
              ]
            }
          ]
        },
        {
          category_id: 101,
          name: '父级二',
          parent_id: 0,
          son: [
            {
              category_id: 104,
              name: '子级一',
              parent_id: 101
            }
          ]
        },
        {
          category_id: 102,
          name: '父级三',
          parent_id: 0,
          son: []
        }
      ]
    }
  },
  methods: {
    // 展开或者隐藏下级目录
    showHidden(item) {
      if (this.showArr.includes(item.category_id)) {
        // 存在
        this.remove(this.showArr, item.category_id)
      } else {
        // 不存在
        this.showArr.push(item.category_id)
      }
    },
    remove(arr, item) {
      for (let index = 0; index < arr.length; index++) {
        const element = arr[index]
        if (element == item) {
          arr.splice(index, 1)
        }
      }
    },
    // 是否展示
    findTrue(id) {
      return this.showArr.includes(id)
    }
  }
}
</script>

<style>
.bg_row {
  width: 100%;
  height: 50px;
  line-height: 50px;
  background-color: #eeeeee;
  color: #000000;
  margin: 2px 0 2px 0;
}

.draicon {
  font-size: 32px;
  color: #7e7e7e;
  padding-left: 6px;
}

</style>