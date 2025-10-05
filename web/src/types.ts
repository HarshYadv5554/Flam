export interface FrameStats {
    totalFrames: number;
    averageProcessingTime: number;
    currentFPS: number;
    lastUpdateTime: number;
}

export interface ProcessedFrame {
    data: string; // Base64 encoded image data
    width: number;
    height: number;
    timestamp: number;
    processingTime: number;
}

export interface FrameData {
    frameData: string; // Base64 encoded frame data
    width: number;
    height: number;
    timestamp: number;
}

export interface WebSocketMessage {
    type: 'frame' | 'stats' | 'error';
    data: ProcessedFrame | FrameStats | string;
}
