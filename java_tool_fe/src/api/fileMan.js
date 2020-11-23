import request from '@/utils/request'

const Qs = require('qs')

/**
 * mongodb 文件管理 \ 新增附件
 */
export function saveFile(form) {
    return request({
        url: '/api/file/save',
        method: 'post',
        data: form
    })
}

/**
 * mongodb 文件管理 \ 查询文件
 */
export function queryFile(fileName) {
    let jsonData={
        fileName:fileName
    }
    return request({
        url: '/api/file/query',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}



/**
 * 文件管理 \ poi 上传文件
 */
export function poiSaveFile(form) {
    return request({
        url: '/api/poi/imp',
        method: 'post',
        data: form
    })
}
