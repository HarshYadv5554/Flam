#include "opencv_processor.h"
#include <android/log.h>

#define LOG_TAG "OpenCVProcessor"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

OpenCVProcessor::OpenCVProcessor() 
    : lowThreshold(50.0)
    , highThreshold(150.0)
    , kernelSize(3)
    , frameCount(0)
    , totalProcessingTime(0.0) {
    
    LOGI("OpenCVProcessor initialized");
}

OpenCVProcessor::~OpenCVProcessor() {
    LOGI("OpenCVProcessor destroyed");
}

cv::Mat OpenCVProcessor::processFrame(const cv::Mat& input) {
    auto startTime = std::chrono::high_resolution_clock::now();
    
    if (input.empty()) {
        LOGE("Input frame is empty");
        return input;
    }
    
    cv::Mat processed;
    
    try {
        // Convert to grayscale if needed
        cv::Mat gray;
        if (input.channels() == 3) {
            cv::cvtColor(input, gray, cv::COLOR_BGR2GRAY);
        } else {
            gray = input.clone();
        }
        
        // Apply Gaussian blur to reduce noise
        cv::Mat blurred = applyGaussianBlur(gray);
        
        // Apply Canny edge detection
        processed = applyCannyEdgeDetection(blurred);
        
        // Convert back to 3-channel for display
        cv::cvtColor(processed, processed, cv::COLOR_GRAY2BGR);
        
    } catch (const cv::Exception& e) {
        LOGE("OpenCV exception: %s", e.what());
        processed = input.clone();
    }
    
    auto endTime = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(endTime - startTime);
    double processingTime = duration.count() / 1000.0; // Convert to milliseconds
    
    updateStats(processingTime);
    
    return processed;
}

cv::Mat OpenCVProcessor::applyCannyEdgeDetection(const cv::Mat& input) {
    cv::Mat edges;
    cv::Canny(input, edges, lowThreshold, highThreshold, kernelSize);
    return edges;
}

cv::Mat OpenCVProcessor::applyGrayscaleFilter(const cv::Mat& input) {
    cv::Mat gray;
    if (input.channels() == 3) {
        cv::cvtColor(input, gray, cv::COLOR_BGR2GRAY);
    } else {
        gray = input.clone();
    }
    return gray;
}

cv::Mat OpenCVProcessor::applyGaussianBlur(const cv::Mat& input) {
    cv::Mat blurred;
    cv::GaussianBlur(input, blurred, cv::Size(5, 5), 0);
    return blurred;
}

void OpenCVProcessor::updateStats(double processingTime) {
    frameCount++;
    totalProcessingTime += processingTime;
    lastProcessTime = std::chrono::high_resolution_clock::now();
    
    // Log stats every 30 frames
    if (frameCount % 30 == 0) {
        double avgProcessingTime = totalProcessingTime / frameCount;
        double fps = 1000.0 / avgProcessingTime;
        LOGI("Frame %d: Processing time: %.2f ms, FPS: %.2f", 
             frameCount, avgProcessingTime, fps);
    }
}

std::string OpenCVProcessor::getStats() {
    if (frameCount == 0) {
        return "No frames processed";
    }
    
    double avgProcessingTime = totalProcessingTime / frameCount;
    double fps = 1000.0 / avgProcessingTime;
    
    return "Frames: " + std::to_string(frameCount) + 
           ", Avg Time: " + std::to_string(avgProcessingTime) + " ms" +
           ", FPS: " + std::to_string(fps);
}
