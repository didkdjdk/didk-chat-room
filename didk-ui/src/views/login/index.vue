<template>
  <div class="login-container">
    <el-card class="login-box" shadow="hover">
      <h2 class="title">{{ isLogin ? 'DIDK 聊天室 - 登录' : 'DIDK 聊天室 - 注册' }}</h2>

      <el-form v-if="isLogin" :model="loginForm" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" size="large" show-password />
        </el-form-item>

        <el-form-item>
          <div style="display: flex; width: 100%;">
            <el-input v-model="loginForm.captcha" placeholder="请输入验证码" size="large" style="flex: 1; margin-right: 10px;" />
            <img
                v-if="captchaUrl"
                :src="captchaUrl"
                @click="refreshCaptcha"
                alt="验证码"
                title="点击刷新验证码"
                class="captcha-img"
            />
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="submit-btn" size="large" :loading="loading">
            登录
          </el-button>
        </el-form-item>
        <div class="toggle-text">
          <span @click="toggleMode">没有账号？去注册</span>
        </div>
      </el-form>

      <el-form v-else :model="registerForm" @keyup.enter="handleRegister">
        <el-form-item>
          <el-input v-model="registerForm.username" placeholder="请输入注册用户名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="handleRegister" class="submit-btn" size="large" :loading="loading">
            注册
          </el-button>
        </el-form-item>
        <div class="toggle-text">
          <span @click="toggleMode">已有账号？去登录</span>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()

// 控制当前是登录还是注册模式
const isLogin = ref(true)
const loading = ref(false)

// 验证码图片地址
const captchaUrl = ref('')

// 生成 UUID 的辅助方法
const generateUuid = () => {
  return typeof crypto.randomUUID === 'function'
      ? crypto.randomUUID()
      : Date.now().toString() + Math.floor(Math.random() * 10000)
}

// 登录表单数据，初始化时置空
const loginForm = ref({
  username: 'admin',
  password: '123',
  uuid: '',
  captcha: ''
})

// 注册表单数据
const registerForm = ref({
  username: '',
  password: '',
  confirmPassword: ''
})

// 刷新验证码方法
const refreshCaptcha = () => {
  loginForm.value.captcha = '' // 刷新时清空已输入的验证码
  loginForm.value.uuid = generateUuid() // 生成新的 uuid

  // 拼接图片请求地址
  // 必须加上 /api 前缀，让 Vite 代理服务器将其转发到网关 (8081)
  // 必须加上 /auth 前缀，让网关将其路由到 didk-auth-server
  captchaUrl.value = `/api/auth/captcha?uuid=${loginForm.value.uuid}`
}

// 页面加载完成时，自动获取一次验证码
onMounted(() => {
  refreshCaptcha()
})

// 切换 登录/注册 模式
const toggleMode = () => {
  isLogin.value = !isLogin.value
  if (isLogin.value) {
    refreshCaptcha() // 切回登录时刷新一下验证码
  } else {
    registerForm.value.username = ''
    registerForm.value.password = ''
    registerForm.value.confirmPassword = ''
  }
}

// 登录处理逻辑
const handleLogin = async () => {
  if (!loginForm.value.username || !loginForm.value.password || !loginForm.value.captcha) {
    ElMessage.warning('请输入完整登录信息！')
    return
  }

  loading.value = true
  try {
    const res = await request.post('/auth/login', loginForm.value)

    const token = res.accessToken || res.data?.accessToken || res

    localStorage.setItem('DIDK_TOKEN', token)
    localStorage.setItem('DIDK_USERNAME', loginForm.value.username)

    ElMessage.success('登录成功！')
    router.push('/chat')
  } catch (error) {
    console.error('登录异常:', error)
    // 如果登录失败（密码错误、验证码错误等），为了安全起见，自动刷新验证码
    refreshCaptcha()
  } finally {
    loading.value = false
  }
}

// 注册处理逻辑
const handleRegister = async () => {
  if (!registerForm.value.username || !registerForm.value.password) {
    ElMessage.warning('请输入完整注册信息！')
    return
  }
  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    ElMessage.warning('两次输入的密码不一致！')
    return
  }

  loading.value = true
  try {
    await request.post('/auth/user/save', {
      username: registerForm.value.username,
      password: registerForm.value.password
    })

    ElMessage.success('注册成功，请进行登录！')
    loginForm.value.username = registerForm.value.username
    loginForm.value.password = ''
    isLogin.value = true
    refreshCaptcha() // 切回登录界面后获取最新验证码
  } catch (error) {
    console.error('注册异常:', error)
    ElMessage.error(error.message || '注册失败，可能用户名已存在或参数缺失！')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-box {
  width: 400px;
  padding: 20px;
  border-radius: 10px;
}
.title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
.captcha-img {
  height: 40px;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
}
.submit-btn {
  width: 100%;
  font-size: 16px;
  margin-top: 10px;
}
.toggle-text {
  text-align: right;
  font-size: 14px;
  color: #409eff;
  cursor: pointer;
  margin-top: 10px;
}
.toggle-text span:hover {
  text-decoration: underline;
}
</style>