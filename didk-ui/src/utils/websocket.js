export class ChatWebSocket {
    constructor(username) {
        this.ws = null;
        this.isReconnect = true;

        const protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
        const host = window.location.host;
        const token = localStorage.getItem('DIDK_TOKEN');

        // 匹配网关 /chat/** 路由 和 服务端 @ServerEndpoint("/chat/{userName}")
        this.url = `${protocol}${host}/ws-api/chat/${username}?token=${token}`;
    }

    connect(onMessage) {
        this.ws = new WebSocket(this.url);

        this.ws.onopen = () => console.log('✅ WebSocket 连接成功');

        this.ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            onMessage(data);
        };

        this.ws.onclose = (e) => {
            console.log('❌ WebSocket 断开', e.code, e.reason);
            if (this.isReconnect) {
                setTimeout(() => this.connect(onMessage), 3000);
            }
        };

        this.ws.onerror = (error) => console.error('WebSocket 异常', error);
    }

    sendMessage(message) {
        if (this.ws && this.ws.readyState === WebSocket.OPEN) {
            this.ws.send(JSON.stringify(message));
        } else {
            console.error('WebSocket 尚未连接');
        }
    }

    close() {
        this.isReconnect = false;
        if (this.ws) this.ws.close();
    }
}