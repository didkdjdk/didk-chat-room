<template>
  <div class="chat-container">
    <div class="sidebar">
      <div class="user-info">
        当前登录：<b>{{ currentUsername }}</b>
        <el-button link type="danger" @click="logout" style="float: right">退出</el-button>
      </div>
      <div
          class="session-item"
          v-for="session in sessions"
          :key="session.id"
          :class="{ active: currentSession?.id === session.id }"
          @click="selectSession(session)"
      >
        {{ session.name || session.roomName || '未命名会话' }}
      </div>
      <div v-if="sessions.length === 0" style="padding: 20px; color: #999; text-align: center;">暂无会话</div>
    </div>

    <div class="chat-area" v-if="currentSession">
      <div class="message-list" id="msg-box">
        <div v-for="(msg, index) in messages" :key="index" class="message-item" :class="{ 'is-me': msg.senderName === currentUsername }">
          <div class="sender">{{ msg.senderName || msg.fromUser }}</div>
          <div class="content">{{ msg.content || msg.msg }}</div>
        </div>
      </div>

      <div class="input-area">
        <el-input
            v-model="inputText"
            type="textarea"
            :rows="4"
            placeholder="按下回车发送消息..."
            @keyup.enter.prevent="sendMsg"
        />
        <div style="text-align: right; margin-top: 10px;">
          <el-button type="primary" @click="sendMsg">发送 (Enter)</el-button>
        </div>
      </div>
    </div>

    <div class="chat-area empty-state" v-else>
      <span>请在左侧选择一个会话开始聊天</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import request from '@/utils/request'
import { ChatWebSocket } from '@/utils/websocket'

const router = useRouter()
const sessions = ref([])
const currentSession = ref(null)
const messages = ref([])
const inputText = ref('')
const currentUsername = ref(localStorage.getItem('DIDK_USERNAME') || '未知用户')

let wsClient = null

onMounted(async () => {
  try {
    // 1. 初始化 WebSocket
    wsClient = new ChatWebSocket(currentUsername.value)
    wsClient.connect((data) => {
      console.log('⬇️ 收到 WebSocket 消息:', data)
      // 根据你的 WSBaseResp / ChatMessageResp 结构进行判断追加
      // 这里假设消息实体在 data.data 或 data.message 里
      const msgEntity = data.data || data.message || data
      if (msgEntity && currentSession.value) {
        // 如果需要严格区分 sessionId，加上 && msgEntity.sessionId === currentSession.value.id
        messages.value.push(msgEntity)
        scrollToBottom()
      }
    })

    // 2. 获取初始会话列表
    // 【需确认】请核对你后端的 ChatSessionController 获取列表的实际路径
    const res = await request.get('/chat/session/listConversations')
    sessions.value = Array.isArray(res) ? res : (res.records || [])

  } catch (error) {
    console.error("数据加载失败:", error)
  }
})

onUnmounted(() => {
  if (wsClient) wsClient.close()
})

const selectSession = async (session) => {
  currentSession.value = session
  try {
    let res;
    // 这里需要根据你 session 对象里的实际字段判断是群聊还是私聊
    // 假设 type === 1 是群聊，type === 0 是私聊 (请根据你的 ChatConversationListItemVO 实际字段调整)
    if (session.type === 1 || session.roomId) {
      // 获取群聊消息
      res = await request.get(`/chat/message/listRoomMessages?roomId=${session.id || session.roomId}`)
    } else {
      // 获取私聊消息
      res = await request.get(`/chat/message/listFriendMessages?friendId=${session.id || session.friendId}`)
    }

    messages.value = Array.isArray(res) ? res : (res.records || [])
    scrollToBottom()
  } catch (error) {
    console.error("历史消息加载失败:", error)
  }
}

const sendMsg = () => {
  const text = inputText.value.trim()
  if (!text) return

  // 根据 com.didk.websocket.model.request.WSBaseReq 组装数据
  const payload = {
    type: 1, // 根据 WSReqTypeEnum.CHAT 对应的值 (通常为数字 1 或字符串 'CHAT')
    data: {
      sessionId: currentSession.value.id,
      content: text
    }
  }

  wsClient.sendMessage(payload)

  // 本地先追加气泡显示
  messages.value.push({
    senderName: currentUsername.value,
    content: text
  })

  inputText.value = ''
  scrollToBottom()
}

const scrollToBottom = () => {
  nextTick(() => {
    const box = document.getElementById('msg-box')
    if (box) box.scrollTop = box.scrollHeight
  })
}

const logout = () => {
  localStorage.removeItem('DIDK_TOKEN')
  localStorage.removeItem('DIDK_USERNAME')
  router.push('/login')
}
</script>

<style scoped>
.chat-container { display: flex; height: 100vh; background: #fff; }
.sidebar { width: 280px; border-right: 1px solid #e6e6e6; background: #fafafa; display: flex; flex-direction: column; }
.user-info { padding: 15px; border-bottom: 1px solid #e6e6e6; background: #eee; font-size: 14px; }
.session-item { padding: 15px; cursor: pointer; border-bottom: 1px solid #f0f0f0; transition: background 0.3s; }
.session-item:hover { background: #f0f0f0; }
.session-item.active { background: #e0f0ff; border-left: 3px solid #409eff; }
.chat-area { flex: 1; display: flex; flex-direction: column; }
.empty-state { justify-content: center; align-items: center; color: #999; background: #f5f7fa; }
.message-list { flex: 1; padding: 20px; overflow-y: auto; background: #f5f7fa; }
.message-item { margin-bottom: 20px; display: flex; flex-direction: column; align-items: flex-start; }
.message-item.is-me { align-items: flex-end; }
.message-item .sender { font-size: 12px; color: #999; margin-bottom: 5px; }
.message-item .content { background: #fff; padding: 10px 15px; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); max-width: 70%; word-break: break-all; }
.message-item.is-me .content { background: #95ec69; }
.input-area { padding: 15px; border-top: 1px solid #e6e6e6; background: #fff; }
</style>