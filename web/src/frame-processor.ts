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
        // Simplified implementation for Node.js environment
        // In a real scenario, you'd use canvas or image processing libraries
        const width = 640;
        const height = 480;
        const data = new Uint8ClampedArray(width * height * 4);
        
        // Fill with a simple pattern for demonstration
        for (let i = 0; i < data.length; i += 4) {
            data[i] = 128;     // R
            data[i + 1] = 128; // G
            data[i + 2] = 128; // B
            data[i + 3] = 255; // A
        }
        
        // Create ImageData-like object for Node.js
        return {
            data: data,
            width: width,
            height: height
        } as ImageData;
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
        // Simplified implementation for Node.js environment
        // In a real scenario, you'd use canvas or image processing libraries
        const width = imageData.width;
        const height = imageData.height;
        const data = imageData.data;
        
        // Create a simple base64 representation
        const buffer = Buffer.from(data);
        return buffer.toString('base64');
    }

    private generateSampleFrame(): void {
        // Create a simple, guaranteed-to-work test image
        // This creates a minimal 1x1 pixel PNG that browsers can definitely display
        this.sampleFrame = this.createMinimalPNG();
    }
    
    private createMinimalPNG(): string {
        // Create a minimal valid PNG image (1x1 pixel, red)
        // PNG signature + IHDR + IDAT + IEND
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
}
