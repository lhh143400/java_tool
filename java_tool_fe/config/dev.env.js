'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    // BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
    // BASE_API: '"https://www.easy-mock.com/mock/5b7d2e5bfe4ed456a980976f/portrait"',//portrait mock
    BASE_URL: '"http://localhost:9528/"',//开发环境配置跨域（本地地址）
})
