<template>
  <div class="login-container">
    <el-card class="login-box" shadow="hover">
      <h2 class="title">DIDK 聊天室</h2>
      <el-form :model="loginForm" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-input v-model="loginForm.captcha" placeholder="请输入验证码" size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-btn" size="large" :loading="loading">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()

// 增加了 uuid 和 captcha 字段，满足后端 LoginController 需求
const loginForm = ref({
  username: 'admin',
  password: '123',
  uuid: crypto.randomUUID ? crypto.randomUUID() : Date.now().toString(),
  captcha: '1234' // 测试验证码，如果后端有固定验证码可以填在这里
})
const loading = ref(false)

const handleLogin = async () => {
  if (!loginForm.value.username || !loginForm.value.password || !loginForm.value.captcha) {
    ElMessage.warning('请输入完整信息！')
    return
  }

  loading.value = true
  try {
    // 调用登录接口
    const res = await request.post('/auth/login', loginForm.value)

    // 你的后端返回的是 Result 对象，其中包含 accessToken (注释已修改，不会再报错)
    const token = res.accessToken || res.data?.accessToken || res

    // 保存真实的 Token 和 用户名
    localStorage.setItem('DIDK_TOKEN', token)
    localStorage.setItem('DIDK_USERNAME', loginForm.value.username)

    ElMessage.success('登录成功！')
    router.push('/chat')
  } catch (error) {
    console.error('登录异常:', error)
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
.login-btn {
  width: 100%;
  font-size: 16px;
  margin-top: 10px;
}
</style>