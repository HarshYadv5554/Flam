import { FrameStats, ProcessedFrame, FrameData } from './types';

export class FrameProcessor {
    private totalFrames: number = 0;
    private totalProcessingTime: number = 0;
    private lastUpdateTime: number = Date.now();
    private sampleFrame: string = '';

    constructor() {
        this.generateSampleFrame();
    }

    public processFrame(frameData: string, width: number, height: number): ProcessedFrame {
        const startTime = Date.now();
        
        // Simulate edge detection processing
        const processedData = this.simulateEdgeDetection(frameData);
        
        const processingTime = Date.now() - startTime;
        
        // Update statistics
        this.totalFrames++;
        this.totalProcessingTime += processingTime;
        this.lastUpdateTime = Date.now();
        
        return {
            data: processedData,
            width,
            height,
            timestamp: Date.now(),
            processingTime
        };
    }

    public getStats(): FrameStats {
        const averageProcessingTime = this.totalFrames > 0 
            ? this.totalProcessingTime / this.totalFrames 
            : 0;
        
        const currentFPS = averageProcessingTime > 0 
            ? 1000 / averageProcessingTime 
            : 0;

        return {
            totalFrames: this.totalFrames,
            averageProcessingTime,
            currentFPS,
            lastUpdateTime: this.lastUpdateTime
        };
    }

    public getSampleFrame(): string {
        return this.sampleFrame;
    }

    private simulateEdgeDetection(frameData: string): string {
        // In a real implementation, this would use OpenCV.js or similar
        // For now, we'll simulate edge detection by manipulating the image data
        
        try {
            // Convert base64 to image data
            const imageData = this.base64ToImageData(frameData);
            
            // Apply simple edge detection simulation
            const processedData = this.applyEdgeDetectionSimulation(imageData);
            
            // Convert back to base64
            return this.imageDataToBase64(processedData);
            
        } catch (error) {
            console.error('Error in edge detection simulation:', error);
            return frameData; // Return original if processing fails
        }
    }

    private base64ToImageData(base64: string): ImageData {
        // This is a simplified implementation
        // In a real scenario, you'd use canvas or image processing libraries
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        const img = new Image();
        
        img.src = `data:image/jpeg;base64,${base64}`;
        
        canvas.width = img.width;
        canvas.height = img.height;
        ctx?.drawImage(img, 0, 0);
        
        return ctx?.getImageData(0, 0, canvas.width, canvas.height) || new ImageData(1, 1);
    }

    private applyEdgeDetectionSimulation(imageData: ImageData): ImageData {
        const data = imageData.data;
        const width = imageData.width;
        const height = imageData.height;
        
        // Simple edge detection simulation
        for (let i = 0; i < data.length; i += 4) {
            const r = data[i];
            const g = data[i + 1];
            const b = data[i + 2];
            
            // Convert to grayscale
            const gray = 0.299 * r + 0.587 * g + 0.114 * b;
            
            // Apply edge detection (simplified)
            const edge = gray > 128 ? 255 : 0;
            
            data[i] = edge;     // R
            data[i + 1] = edge; // G
            data[i + 2] = edge; // B
            // Alpha channel remains unchanged
        }
        
        return imageData;
    }

    private imageDataToBase64(imageData: ImageData): string {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        
        canvas.width = imageData.width;
        canvas.height = imageData.height;
        ctx?.putImageData(imageData, 0, 0);
        
        return canvas.toDataURL('image/jpeg').split(',')[1];
    }

    private generateSampleFrame(): void {
        // Generate a sample frame for demonstration
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        
        canvas.width = 640;
        canvas.height = 480;
        
        if (ctx) {
            // Draw a simple pattern
            ctx.fillStyle = '#ffffff';
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            
            ctx.fillStyle = '#000000';
            ctx.fillRect(100, 100, 200, 200);
            
            ctx.fillStyle = '#ff0000';
            ctx.beginPath();
            ctx.arc(320, 240, 50, 0, 2 * Math.PI);
            ctx.fill();
        }
        
        this.sampleFrame = canvas.toDataURL('image/jpeg').split(',')[1];
    }
}
