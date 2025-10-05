import { WebSocket, WebSocketServer as WSWebSocketServer } from 'ws';
import { WebSocketMessage, ProcessedFrame, FrameStats } from './types';

export class WebSocketServer {
    private wss: WSWebSocketServer;
    private clients: Set<WebSocket> = new Set();

    constructor() {
        this.wss = new WSWebSocketServer({ port: 8080 });
        this.setupWebSocketServer();
    }

    private setupWebSocketServer(): void {
        this.wss.on('connection', (ws: WebSocket) => {
            console.log('New WebSocket client connected');
            this.clients.add(ws);

            // Send welcome message
            this.sendMessage(ws, {
                type: 'stats',
                data: {
                    totalFrames: 0,
                    averageProcessingTime: 0,
                    currentFPS: 0,
                    lastUpdateTime: Date.now()
                } as FrameStats
            });

            ws.on('close', () => {
                console.log('WebSocket client disconnected');
                this.clients.delete(ws);
            });

            ws.on('error', (error) => {
                console.error('WebSocket error:', error);
                this.clients.delete(ws);
            });
        });

        console.log('WebSocket server started on port 8080');
    }

    public broadcastFrame(frame: ProcessedFrame, width: number, height: number, timestamp?: number): void {
        const message: WebSocketMessage = {
            type: 'frame',
            data: {
                ...frame,
                width,
                height,
                timestamp: timestamp || Date.now()
            }
        };

        this.broadcast(message);
    }

    public broadcastStats(stats: FrameStats): void {
        const message: WebSocketMessage = {
            type: 'stats',
            data: stats
        };

        this.broadcast(message);
    }

    public broadcastError(error: string): void {
        const message: WebSocketMessage = {
            type: 'error',
            data: error
        };

        this.broadcast(message);
    }

    private broadcast(message: WebSocketMessage): void {
        const messageStr = JSON.stringify(message);
        
        this.clients.forEach((client) => {
            if (client.readyState === WebSocket.OPEN) {
                try {
                    client.send(messageStr);
                } catch (error) {
                    console.error('Error sending WebSocket message:', error);
                    this.clients.delete(client);
                }
            }
        });
    }

    private sendMessage(ws: WebSocket, message: WebSocketMessage): void {
        if (ws.readyState === WebSocket.OPEN) {
            try {
                ws.send(JSON.stringify(message));
            } catch (error) {
                console.error('Error sending WebSocket message:', error);
            }
        }
    }

    public getConnectedClientsCount(): number {
        return this.clients.size;
    }

    public close(): void {
        this.wss.close();
    }
}
