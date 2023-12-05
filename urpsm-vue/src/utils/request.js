import axios from "axios";
const request = axios.create(
    {
        //注意，这里统一在所有接口上加上“/api”前缀，页面里面写接口就不要再加入“/api”
        // baseURL: '/api',
        baseURL: 'http://localhost:9090',
        //超时等待时间 ，超过此时间如果接口没反馈，就不在等待，5000=5秒
        //前端回报time of 错误
        timeout: 5000
    }
)
// request拦截器
// 可以请求发送前对请求做统一处理
// 比如统一加上token，对请求统一加密
request.interceptors.request.use(config=>{
    config.headers['Content-Type'] = 'application/json;charset=utf-8'

    let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) :null;

    // 把token设置到头部设置请求头部
    if(user){
        config.headers['token'] = user.token;
    }
    return config
},error => {
    return Promise.reject(error)
    });

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response =>{
        let res = response.data;
        // 如果是返回文件
        if(response.config.responseType === 'blob'){return res}
        // 兼容服务端返回的字符串数据
        if(typeof  res === 'string'){
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('error==' + error)
        return Promise.reject(error)
    }
)

export  default  request