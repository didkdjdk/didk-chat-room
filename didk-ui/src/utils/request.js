import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: '/api', // 触发 vite 代理
    timeout: 10000
})

service.interceptors.request.use(
    config => {
        const token = localStorage.getItem('DIDK_TOKEN')
        // 请求头携带 Token，配合后端的 AuthenticationTokenFilter
        if (token) {
            config.headers['token'] = token // 你的后端可能叫 token 或 Authorization，请根据实际情况微调
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config
    },
    error => Promise.reject(error)
)

service.interceptors.response.use(
    response => {
        const res = response.data
        // 假设后端返回 code: 0 表示成功，非 0 为异常
        if (res.code !== 0 && res.code !== 200) {
            ElMessage.error(res.msg || '服务器错误')
            if (res.code === 401) {
                localStorage.removeItem('DIDK_TOKEN')
                localStorage.removeItem('DIDK_USERNAME')
                window.location.href = '/login'
            }
            return Promise.reject(new Error(res.msg || 'Error'))
        }
        return res.data !== undefined ? res.data : res
    },
    error => {
        ElMessage.error(error.message)
        return Promise.reject(error)
    }
)

export default service