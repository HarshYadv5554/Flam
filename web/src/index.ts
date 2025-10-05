import express from 'express';
import cors from 'cors';
import path from 'path';
import { FrameProcessor } from './frame-processor';
import { WebSocketServer } from './websocket-server';
import { FrameStats } from './types';

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, '../public')));

// Initialize components
const frameProcessor = new FrameProcessor();
const wsServer = new WebSocketServer();

// Routes
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../public/index.html'));
});

app.get('/api/stats', (req, res) => {
    const stats: FrameStats = frameProcessor.getStats();
    res.json(stats);
});

app.post('/api/frame', (req, res) => {
    try {
        const { frameData, width, height, timestamp } = req.body;
        
        if (!frameData || !width || !height) {
            return res.status(400).json({ error: 'Invalid frame data' });
        }
        
        // Process frame
        const processedFrame = frameProcessor.processFrame(frameData, width, height);
        
        // Broadcast to WebSocket clients
        wsServer.broadcastFrame(processedFrame, width, height, timestamp);
        
        res.json({ success: true, processedFrame });
        
    } catch (error) {
        console.error('Error processing frame:', error);
        res.status(500).json({ error: 'Frame processing failed' });
    }
});

app.get('/api/sample-frame', (req, res) => {
    try {
        const sampleFrame = frameProcessor.getSampleFrame();
        res.json({ frame: sampleFrame });
    } catch (error) {
        console.error('Error getting sample frame:', error);
        res.status(500).json({ error: 'Failed to get sample frame' });
    }
});

// Start server
app.listen(PORT, () => {
    console.log(`Edge Detection Web Viewer running on port ${PORT}`);
    console.log(`Open http://localhost:${PORT} to view the application`);
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log('Shutting down server...');
    process.exit(0);
});
