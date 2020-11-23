import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const UserNameKey = 'Admin-UserName'
const UserIdKey = 'Admin-UserId'

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

export function removeToken() {
    return Cookies.remove(TokenKey)
}

export function setUserName(username) {
    return Cookies.set(UserNameKey, username)
}

export function getUserName() {
    return Cookies.get(UserNameKey)
}

export function setUserId(userId) {
    return Cookies.set(UserIdKey, userId)
}

export function getUserId() {
    return Cookies.get(UserIdKey)
}

export function removeUserId() {
    return Cookies.remove(UserIdKey)
}
