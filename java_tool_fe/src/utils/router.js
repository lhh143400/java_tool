import userPath from '@/router/asyncRouter'
// 深拷贝
export const deepcopy = function(source) {
    if (!source) {
        return source
    }
    let sourceCopy = source instanceof Array ? [] : {}
    for (let item in source) {
        sourceCopy[item] = typeof source[item] === 'object' ? deepcopy(source[item]) : source[item]
    }
    return sourceCopy
}

// 菜单数据组织
export const buildMenu = function(array, ckey) {
    let menuData = []
    let indexKeys = Array.isArray(array) ? array.map((e) => {
        return e.id
    }) : []
    ckey = ckey || 'parentId'
    array.forEach(function(e, i) {
        // 一级菜单
        if (!e[ckey] || (e[ckey] === e.id)) {
            delete e[ckey]
            menuData.push(deepcopy(e)) // 深拷贝
        } else if (Array.isArray(indexKeys)) {
            // 检测ckey有效性
            let parentIndex = indexKeys.findIndex(function(id) {
                return id == e[ckey]
            })
            if (parentIndex === -1) {
                menuData.push(e)
            }
        }
    })
    let findChildren = function(parentArr) {
        if (Array.isArray(parentArr) && parentArr.length) {
            parentArr.forEach(function(parentNode) {
                array.forEach(function(node) {
                    if (parentNode.id === node[ckey]) {
                        if (parentNode.children) {
                            parentNode.children.push(node)
                        } else {
                            parentNode.children = [node]
                        }
                    }
                })
                if (parentNode.children) {
                    findChildren(parentNode.children)
                }
            })
        }
    }
    findChildren(menuData)
    return menuData
}

// 进行路由处理
export const getRoutes = function(userInfo, root) {
    let vm = this
    let allowedRouter = []
    // 将菜单数据转成多维数组格式
    let arrayMenus = buildMenu(userInfo)
    // 将多维数组转成对象格式
    let hashMenus = {}
    let setMenu2Hash = function(array, base) {
        array.map(key => {
            if (key.moduleUrl) {
                let hashKey = ((base ? base + '/' : '') + key.moduleUrl).replace(/^\//, '')
                hashMenus['/' + hashKey] = true
                if (Array.isArray(key.children)) {
                    setMenu2Hash(key.children, key.moduleUrl)
                }
            }
        })
    }
    setMenu2Hash(arrayMenus)
    // 全局挂载hashMenus，用于实现路由守卫
    root.hashMenus = hashMenus
    // 筛选本地路由方法
    let findLocalRoute = function(array, base) {
        let replyResult = []
        array.forEach(function(route) {
            // 给权限赋值
            for (let i = 0; i < userInfo.length; i++) {
                if (userInfo[i].name === route.name && route.meta.permission === '') {
                    route.meta.permission = userInfo[i].permission
                }
            }
            let pathKey = (base ? base + '/' : '') + route.path
            if (hashMenus[pathKey]) {
                if (Array.isArray(route.children)) {
                    route.children = findLocalRoute(route.children, route.path)
                }
                replyResult.push(route)
            }
        })
        if (base) {
            return replyResult
        } else {
            allowedRouter = allowedRouter.concat(replyResult)
        }
    }
    let originPath = deepcopy(userPath)
    findLocalRoute(originPath)
    return allowedRouter
}
