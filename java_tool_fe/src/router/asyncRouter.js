import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

export const asyncRouter = [
    {
        path: '/',
        component: Layout,
        redirect: '/main',
        name: 'Main',
        hidden: true,
        children: [{
            path: 'main',
            component: () => import('../views/main/index')
        }]
    },
    {
        path: '/file',
        component: Layout,
        redirect: 'noredirect',
        name: 'fileMan',
        meta: { title: '文件管理', icon: 'system' },
        children: [
            {
                path: 'file/mongodb',
                name: 'file',
                component: () => import('../views/file/MongodbFile'),
                meta: { title: 'MongoDb文件', icon: 'system-users' }
            },
            {
                path: 'file/poi',
                name: 'poi',
                component: () => import('../views/file/PoiFile'),
                meta: { title: 'POI', icon: 'system-users' }
            },
            ]

    },

    {
        path: '/elasticsearch',
        component: Layout,
        redirect: 'noredirect',
        name: 'esMan',
        meta: { title: '搜索引擎', icon: 'system' },
        children: [
            {
                path: 'es/index',
                name: 'es',
                component: () => import('../views/elasticsearch/es'),
                meta: { title: 'ES', icon: 'system-users' }
            },
        ]

    },


    //系统管理
    {
        path: '/system',
        component: Layout,
        redirect: 'noredirect',
        name: 'System',
        meta: { title: '系统管理', icon: 'system' },
        children: [
            {
                path: 'users',
                name: 'Users',
                component: () => import('../views/system/users/Users'),
                meta: { title: '用户管理', icon: 'system-users' }
            },
            {
                path: 'roles',
                name: 'Roles',
                component: () => import('../views/system/roles/Roles'),
                meta: { title: '角色管理', icon: 'system-roles' }
            },
            {
                path: 'acls',
                name: 'Permission',
                component: () => import('../views/system/permission/Permission'),
                meta: { title: '权限管理', icon: 'system-functions' }
            },
            {
                path: 'record',
                name: 'Record',
                component: () => import('../views/system/record/Record'),
                meta: { title: '权限更新记录', icon: 'system-record' }
            }
        ]
    },

    {
        path: '/personal',
        component: Layout,
        redirect: 'noredirect',
        name: '个人中心',
        hidden: true,
        meta: { title: '个人中心', icon: 'percenter' },
        children: [
            {
                path: 'setting/index', name: '个人设置', hidden: true,
                component: () => import('@/views/personal/setting/index'),
                meta: { title: '个人设置', icon: 'twoclass' }
            },
            { path: '*', redirect: '/404', hidden: true }
        ]
    },

    { path: '*', redirect: '/404', hidden: true }
]

export default asyncRouter
