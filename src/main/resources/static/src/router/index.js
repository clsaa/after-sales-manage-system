import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('../views/login/index'), hidden: true },
  { path: '/404', component: () => import('../views/404'), hidden: true },
  { path: '/', redirect: '/dashboard/index' },
  { path: '/exposure/workorder/add', component: () => import('../views/exposure/workorder/add'), hidden: true },
  { path: '/exposure/workorder/article', component: () => import('../views/exposure/workorder/article'), hidden: true },
  { path: '/exposure/workorder/index', component: () => import('../views/exposure/workorder/index'), hidden: true },
  { path: '/exposure/workorder/:id', component: () => import('../views/exposure/workorder/update'), hidden: true },

  {
    path: '/dashboard',
    redirect: '/dashboard/index',
    component: Layout,
    name: 'Dashboard',
    hidden: false,
    children: [{
      path: 'index',
      component: () => import('../views/dashboard/index'),
      meta: { title: '仪表盘', icon: 'form' }
    }]
  },
  {
    name: 'Customer',
    path: '/customer',
    component: Layout,
    meta: { title: '客户管理', icon: 'example' },
    children: [
      {
        path: 'index',
        name: 'CustomerList',
        component: () => import('../views/customer/index'),
        meta: { title: '客户列表', icon: 'form' }
      },
      {
        path: 'add',
        name: 'CustomerAdd',
        component: () => import('../views/customer/add'),
        meta: { title: '添加客户', icon: 'form' }
      },
      {
        path: ':id',
        name: 'CustomerDetail',
        component: () => import('../views/customer/update'),
        hidden: true,
        meta: { title: '查看|编辑客户', icon: 'form' }
      }
    ]
  },
  {
    name: 'WorkOrder',
    path: '/workorder',
    component: Layout,
    meta: { title: '工单管理', icon: 'example' },
    children: [
      {
        path: 'index',
        name: 'WorkOrderList',
        component: () => import('../views/workorder/index'),
        meta: { title: '工单列表', icon: 'form' }
      },
      {
        path: ':id',
        name: 'WorkOrderUpdate',
        component: () => import('../views/workorder/update'),
        hidden: true,
        meta: { title: '查看工单', icon: 'form' }
      }
    ]
  },
  {
    name: 'Plan',
    path: '/plan',
    component: Layout,
    meta: { title: '计划管理', icon: 'example' },
    children: [
      {
        path: 'index',
        name: 'PlanList',
        component: () => import('../views/plan/index'),
        meta: { title: '计划列表', icon: 'form' }
      },
      {
        path: 'add',
        name: 'PlanAdd',
        component: () => import('../views/plan/add'),
        meta: { title: '添加计划', icon: 'form' }
      }
    ]
  },
  {
    name: 'Article',
    path: '/article',
    component: Layout,
    meta: { title: '知识库管理', icon: 'example' },
    children: [
      {
        path: 'index',
        name: 'ArticleList',
        component: () => import('../views/article/index'),
        meta: { title: '知识库列表', icon: 'form' }
      },
      {
        path: 'add',
        name: 'ArticleAdd',
        component: () => import('../views/article/add'),
        meta: { title: '添加文章', icon: 'form' }
      },
      {
        path: ':id',
        name: 'RepositoryUpdate',
        component: () => import('../views/article/update'),
        hidden: true,
        meta: { title: '编辑文章', icon: 'form' }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

