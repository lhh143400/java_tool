import request from '@/utils/request'

const Qs = require('qs')

/**
 * 用户管理|部门列表|获取部门树
 */
export function getDeptTree() {
    return request({
        url: '/api/sys/dept/tree',
        method: 'get'
    })
}

/**
 * 用户管理|部门列表|新增部门
 * @param form
 * @param param 新增部门: 名称|父Id|顺序|备注
 */
export function addDept(form) {
    let jsonData = {
        name: form.name,
        parentId: form.selectedOptions[form.selectedOptions.length - 1],
        seq: form.order,
        remark: form.remark
    }
    return request({
        url: '/api/sys/dept/save',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|部门列表|更新部门
 * @param form
 * @param param 更新部门: id|名称|父Id|顺序|备注
 */
export function updateDept(id, form) {
    let jsonData = {
        id: id,
        name: form.name,
        parentId: form.selectedOptions[form.selectedOptions.length - 1],
        seq: form.order,
        remark: form.remark
    }
    return request({
        url: '/api/sys/dept/update',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|部门列表|删除部门
 * @param form
 * @param param 删除部门: id
 */
export function deleteDept(id) {
    let jsonData = {
        id: id
    }
    return request({
        url: '/api/sys/dept/delete',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|用户列表|查询用户列表(通过用户名称)
 * @param form
 * @param param 查询用户列表: 部门ID|页数|大小
 */
export function findUserByPage(page, deptId, keyword) {
    let jsonData = {
        page: page,
        size: 10,
        deptId: deptId,
        keyword: keyword
    }
    return request({
        url: '/api/sys/user/findByPage',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|用户列表|查询用户列表(通过用户角色)
 * @param form
 * @param param 查询用户列表: 部门ID|页数|大小
 */
export function findUserByRoleName(page, deptId, roleName) {
    let jsonData = {
        page: page,
        size: 10,
        deptId: deptId,
        roleName: roleName
    }
    return request({
        url: '/api/sys/user/findByRoleName',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|用户列表|新增用户
 * @param form
 * @param param 新增用户: 用户名|电话|邮箱|部门Id|角色Id|状态|备注
 */
export function addUser(form) {
    let jsonData = {
        username: form.username,
        telephone: form.phone,
        mail: form.mail,
        deptId: form.deptId[form.deptId.length - 1],
        status: form.status,
        remark: form.remark,
        roleIds: form.roleIds
    }
    return request({
        url: '/api/sys/user/save',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|用户列表|更新用户
 * @param form
 * @param param 更新用户: id|用户名|电话|邮箱|部门Id|角色Id|状态|备注
 */
export function updateUser(id, form) {
    let jsonData = {
        id: id,
        username: form.username,
        telephone: form.phone,
        mail: form.mail,
        deptId: form.deptId[form.deptId.length - 1],
        status: form.status,
        remark: form.remark,
        roleIds: form.roleIds
    }
    return request({
        url: '/api/sys/user/update',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}

/**
 * 用户管理|用户列表|删除用户
 * @param form
 * @param param 删除用户: id
 */
export function deleteUser(id) {
    let jsonData = {
        id: id
    }
    return request({
        url: '/api/sys/user/delete',
        method: 'post',
        data: Qs.stringify(jsonData)
    })
}
