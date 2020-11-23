import {login, logout, getInfo} from '@/api/login'
import {getToken, setToken, removeToken, setUserName, getUserName, setUserId, removeUserId} from '@/utils/auth'
import axios from 'axios'

const user = {
    state: {
        token: getToken(),
        name: '',
        avatar: '',
        roles: []
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        }
    },

    actions: {
        // 登录
        Login({commit}, userInfo) {
            const username = userInfo.username.trim()
            return new Promise((resolve, reject) => {
                login(username, userInfo.password).then(response => {
                    const data = response.data
                    setToken(data.token)
                    commit('SET_TOKEN', data.token)
                    setUserName(username)
                    axios.defaults.headers.common['Authorization'] = data.token // 全局所有接口加上这个属性
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 获取用户信息
        GetInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getInfo(getUserName()).then(response => {
                    const data = response.data
                    if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
                        let roleNames = []
                        for (let i = 0; i < data.roles.length; i++) {
                            roleNames.push(data.roles[i].name)
                        }
                        commit('SET_ROLES', roleNames)
                    } else {
                        reject('getInfo: roles must be a non-null array !')
                    }
                    setUserId(data.id)
                    localStorage.setItem('acls', JSON.stringify(data.acls))
                    commit('SET_NAME', data.username)
                    commit('SET_AVATAR', 'static/images/avater.png')
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(getUserName()).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_NAME', '')
                    commit('SET_ROLES', [])
                    removeToken()
                    removeUserId()
                    sessionStorage.clear()
                    localStorage.removeItem('functions')
					localStorage.removeItem('acls')
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_NAME', '')
                commit('SET_ROLES', [])
                removeToken()
                removeUserId()
                sessionStorage.clear()
                localStorage.removeItem('functions')
                localStorage.removeItem('acls')
                resolve()
            })
        }
    }
}

export default user
