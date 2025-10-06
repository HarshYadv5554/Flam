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
        // Create a simple test image that will definitely work
        const testImage = createTestImage();
        res.json({ frame: testImage });
    } catch (error) {
        console.error('Error getting sample frame:', error);
        res.status(500).json({ error: 'Failed to get sample frame' });
    }
});

function createTestImage(): string {
    // Create a simple 1x1 red pixel PNG that browsers can definitely display
    // This is a minimal valid PNG
    const pngData = Buffer.from([
        0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, // PNG signature
        0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52, // IHDR chunk
        0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, // 1x1 pixel
        0x08, 0x02, 0x00, 0x00, 0x00, 0x90, 0x77, 0x53, 0xDE, // IHDR data
        0x00, 0x00, 0x00, 0x0C, 0x49, 0x44, 0x41, 0x54, // IDAT chunk
        0x08, 0x99, 0x01, 0x01, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01, // IDAT data
        0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, 0xAE, 0x42, 0x60, 0x82 // IEND chunk
    ]);
    
    return pngData.toString('base64');
}

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
