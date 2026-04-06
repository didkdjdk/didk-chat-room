import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    port: 3000,
    proxy: {
      // HTTP 请求代理到 Gateway (8081端口)
      '/api': {
        target: 'http://127.0.0.1:8081',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
        configure: (proxy) => {
          proxy.on('error', (err, req, res) => {
            console.log('HTTP 代理异常:', err.message, req.url)
            if (res && res.writeHead) {
              res.writeHead(500, { 'Content-Type': 'text/plain' })
              res.end('Proxy Error')
            }
          })
        }
      },
      // WebSocket 请求代理到 Gateway
      '/ws-api': {
        target: 'ws://127.0.0.1:8081',
        ws: true,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/ws-api/, ''),
        configure: (proxy) => {
          proxy.on('error', (err, req, socket) => {
            if (socket && socket.destroy) socket.destroy()
          })
          proxy.on('proxyReqWs', (proxyReq, req, socket) => {
            socket.on('error', (err) => console.log('WS 底层断开:', err.message))
          })
        }
      }
    }
  }
})