import request from '@/utils/request'

const Qs = require('qs')

export function login(username, password) {
    let loginData = {
        'username': username,
        'password': password
    }
    return request({
        url: '/api/login',
        method: 'post',
        data: Qs.stringify(loginData)
    })
}

export function getInfo(username) {
    let jsonData = {
        'username': username
    }
    return request({
        url: '/api/info',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

export function getFunctions(username) {
    let jsonData = {
        'username': username
    }
    return request({
        url: '/api/functions',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

export function logout(username) {
    let jsonData = {
        'username': username
    }
    return request({
        url: '/api/logout',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}
