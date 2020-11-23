import request from '@/utils/request'

const Qs = require('qs')

/**
 * 权限管理|权限模块列表|获取权限模块树
 */
export function getAclModuleTree() {
    return request({
        url: '/api/sys/aclModule/tree',
        method: 'get'
    })
}

/**
 * 权限管理|权限模块列表|新增权限模块
 * @param form
 * @param param 新增权限模块: 上级模块ID|名称|url|顺序|状态|备注
 */
export function addAclModule(form) {
    let jsonData = {
        parentId: form.selectedOptions[form.selectedOptions.length - 1],
        name: form.name,
        moduleUrl: form.url,
        seq: form.order,
        status: form.status,
        remark: form.remark
    }
    return request({
        url: '/api/sys/aclModule/save',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|更新权限模块
 * @param form
 * @param param 更新权限模块: id|上级模块ID|名称|url|顺序|状态|备注
 */
export function updateAclModule(id, form) {
    let jsonData = {
        id: id,
        parentId: form.selectedOptions[form.selectedOptions.length - 1],
        name: form.name,
        moduleUrl: form.url,
        seq: form.order,
        status: form.status,
        remark: form.remark
    }
    return request({
        url: '/api/sys/aclModule/update',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|删除权限模块
 * @param form
 * @param param 删除权限模块: id
 */
export function deleteAclModule(id) {
    let jsonData = {
        id: id
    }
    return request({
        url: '/api/sys/aclModule/delete',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|查询权限点列表
 * @param form
 * @param param 查询权限点列表: 权限模块ID|页数|大小
 */
export function findAclByPage(page, aclModuleId, keyword) {
    let jsonData = {
        page: page,
        size: 10,
        aclModuleId: aclModuleId,
        keyword: keyword
    }
    return request({
        url: '/api/sys/acl/findByPage',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|新增权限点
 * @param form
 * @param param 新增权限点: 上级模块ID|名称|url|顺序|状态|备注
 */
export function addAcl(form) {
    let jsonData = {
        aclModuleId: form.selectedOptions[form.selectedOptions.length - 1],
        name: form.name,
        url: form.url,
        type: form.type,
        seq: form.seq,
        status: form.status,
        remark: form.remark
    }
    return request({
        url: '/api/sys/acl/save',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|更新权限点
 * @param form
 * @param param 更新权限点: id|上级模块ID|名称|url|顺序|状态|备注
 */
export function updateAcl(id, form) {
    let jsonData = {
        id: id,
        aclModuleId: form.selectedOptions[form.selectedOptions.length - 1],
        name: form.name,
        url: form.url,
        type: form.type,
        seq: form.seq,
        status: form.status,
        remark: form.remark
    }
    return request({
        url: '/api/sys/acl/update',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 权限管理|权限模块列表|删除权限点
 * @param form
 * @param param 删除权限点: id
 */
export function deleteAcl(id) {
    let jsonData = {
        id: id
    }
    return request({
        url: '/api/sys/acl/delete',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}
