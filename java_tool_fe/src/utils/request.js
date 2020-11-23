import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import { Notification } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
    baseURL: process.env.BASE_URL, // api的base_url
    timeout: 10000000 // 请求超时时间
})

// axios.defaults.withCredentials = true
axios.defaults.headers.common['Authorization'] = getToken()

// request拦截器
service.interceptors.request.use(
    config => {
        if (store.getters.token) {
            config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
        }
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    }
)

// respone拦截器
service.interceptors.response.use(
    response => {
        /**
         * code为非20000是抛错 可结合自己业务进行修改
         */
        const res = response.data
        if (res.code !== 200) {
            Notification({
                title: '错误',
                message: res.message,
                type: 'error'
            })
            // Message({
            //     message: res.message,
            //     type: 'error',
            //     duration: 5 * 1000
            // })

            // 508:非法的token; 512:其他客户端登录了;  514:Token 过期了;
            if (res.code === 508 || res.code === 512 || res.code === 514) {
                MessageBox.confirm(
                    '你已被登出，可以取消继续留在该页面，或者重新登录',
                    '确定登出',
                    {
                        confirmButtonText: '重新登录',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    store.dispatch('FedLogOut').then(() => {
                        location.reload() // 为了重新实例化vue-router对象 避免bug
                    })
                })
            }
            return Promise.reject('error')
        } else {
            return response.data
        }
    },
    error => {
        console.log('err' + error) // for debug
        // Message({
        //     message: error.message,
        //     type: 'error',
        //     duration: 5 * 1000
        // })
        Notification({
            title: '错误',
            message: error.message,
            type: 'error'
        })
        return Promise.reject(error)
    }
)

export default service
