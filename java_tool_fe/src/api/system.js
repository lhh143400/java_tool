import request from '@/utils/request'

const Qs = require('qs')

/**
 * 权限更新记录|权限更新记录列表|查询权限更新记录列表
 * @param form
 * @param param 查询权限更新记录: 页数|大小
 */
export function findLogByPage(page, type, oldValue, newValue, operator) {
  const jsonData = {
    page: page,
    size: 10,
    type: type,
    oldValue: oldValue,
    newValue: newValue,
    operator: operator
  }
  return request({
    url: '/api/sys/log/findByPage',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 权限更新记录|权限更新记录列表|还原记录
 * @param form
 * @param param 还原记录: id
 */
export function recover(id) {
  const jsonData = {
    id: id
  }
  return request({
    url: '/api/sys/log/recover',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 校验旧密码
 * @param form
 * @param param 校验旧密码: password
 */
export function checkPwd(password) {
  const jsonData = {
    password: password
  }
  return request({
    url: '/api/sys/user/validate',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}

/**
 * 修改密码
 * @param form
 * @param param 修改密码: password
 */
export function updatePassword(password) {
  const jsonData = {
    password: password
  }
  return request({
    url: '/api/sys/user/change',
    method: 'post',
    data: Qs.stringify(jsonData)
  })
}
