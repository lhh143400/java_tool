import request from '@/utils/request'

const Qs = require('qs')

/**
 * 角色管理|角色列表|查询所有角色
 */
export function getAllRoles() {
  return request({
    url: '/api/sys/role/findAll',
    method: 'get'
  })
}

/**
 * 角色管理|角色列表|新增角色
 * @param form
 * @param param 新增角色: 名称|类型|状态|备注
 */
export function addRole(form) {
  const jsonData = {
    name: form.name,
    type: form.type,
    status: form.status,
    remark: form.remark
  }
  return request({
    url: '/api/sys/role/save',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 角色管理|角色列表|更新角色
 * @param form
 * @param param 更新角色: id|名称|类型|状态|备注
 */
export function updateRole(id, form) {
  const jsonData = {
    id: id,
    name: form.name,
    type: form.type,
    status: form.status,
    remark: form.remark
  }
  return request({
    url: '/api/sys/role/update',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 角色管理|角色列表|删除角色
 * @param form
 * @param param 删除角色: id
 */
export function deleteRole(id) {
  const jsonData = {
    id: id
  }
  return request({
    url: '/api/sys/role/delete',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 角色管理|角色列表|加载角色与权限树
 * @param form
 * @param param 加载角色与权限树: roleId
 */
export function getRolesTree(roleId) {
  const jsonData = {
    roleId: roleId
  }
  return request({
    url: '/api/sys/role/roleTree',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 角色管理|角色列表|角色与权限保存
 * @param form
 * @param param 角色与权限保存: roleId|aclIds
 */
export function changeAcls(roleId, aclIds) {
  const jsonData = {
    roleId: roleId,
    aclIds: aclIds
  }
  return request({
    url: '/api/sys/role/changeAcls',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}
