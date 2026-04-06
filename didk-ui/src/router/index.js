import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/', redirect: '/chat' },
    { path: '/login', name: 'Login', component: () => import('@/views/login/index.vue') },
    { path: '/chat', name: 'Chat', component: () => import('@/views/chat/index.vue') }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 全局路由守卫：未登录时强制跳转到 /login
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('DIDK_TOKEN')
    if (to.path !== '/login' && !token) {
        next('/login')
    } else {
        next()
    }
})

export default router