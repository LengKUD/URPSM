import Vue from 'vue'
import VueRouter from 'vue-router'


// 避免重复点击一个链接报错
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login'),
  },
  {
    path: '/regis',
    name: 'Regis',
    component: () => import('../views/Regis'),
  },
  {
    path: '/404',
    name: 'NotFound',
    component: () => import('../views/404.vue')
  }
]
const createRouter = ()=>{
    new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// const router = createRouter()

export const resetUserRoutes =() =>{
    // let newRoutes = createRouter()
    // router.matcher = newRoutes
    router.matcher = new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}




const addUserRoutes = (routes,menu) =>{
    if(menu && menu.length>0){
        menu.forEach(item =>{
            if(item.path){
                let menu = {path: item.path.replace('/',''), name: item.name,component: () => import('../views/'+ item.pagePath)}
                routes.children.push(menu);
            }
            if(item.children && item.children.length>0){
                addUserRoutes(routes,item.children)
            }
         })
    }

}

// 动态设置登录用户的路由
export const setUserRoutes = () =>{


    // 每次添加前先清除路由，重新加载，否则会添加重复路由，新的路由也添加不进入
    resetUserRoutes();



    const userMenu = localStorage.getItem("user") ?JSON.parse(localStorage.getItem("user")).menuList :null;

    if(userMenu && userMenu.length>0){
        // 次常量只能放在方法里面，否则每次都会添加重复的子路由
        const userRoutes = {
            path: '/',
            name: 'Manage',
            component: () => import('../views/Manage.vue'),
            redirect: '/home',
            children:[
                {path: '/userinfoEdit', name: 'UserinfoEdit', component: () => import('../views/UserEdit'),children:[]},
                {path: '/changePWD', name: '修改密码', component: () => import('../views/UserEdit'),children:[]},
            ]
        }

        addUserRoutes(userRoutes,userMenu)

        router.addRoute(userRoutes);


        // console.log('router.matcher')
        // console.log(router.matcher)

    }

}


setUserRoutes()


// 路由守卫
router.beforeEach((to, from, next)=>{

    // 访问路由没找到的情况,访问路由是否在路由列表中
    if(!to.matched.length){
        const userMenu = localStorage.getItem("user") ?JSON.parse(localStorage.getItem("user")).menuList :null;
        // 判断用户是否登录成功，用户路由是否存在 ，如果登录成功就跳转404，没有成果则跳转到"/login"路由
        //如果登录成果说明访问的路由不在路由列表中，就跳转到404界面，如果没有登录，直接跳转登录界面。
        if(userMenu){
            next("/404")
        }else{
            next("login")
        }

    }
    next()


})
    


export default router
