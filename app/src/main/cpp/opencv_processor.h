#ifndef OPENCV_PROCESSOR_H
#define OPENCV_PROCESSOR_H

#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <chrono>
#include <string>

class OpenCVProcessor {
public:
    OpenCVProcessor();
    ~OpenCVProcessor();
    
    cv::Mat processFrame(const cv::Mat& input);
    std::string getStats();
    
private:
    // Edge detection parameters
    double lowThreshold;
    double highThreshold;
    int kernelSize;
    
    // Performance tracking
    std::chrono::high_resolution_clock::time_point lastProcessTime;
    int frameCount;
    double totalProcessingTime;
    
    // Processing methods
    cv::Mat applyCannyEdgeDetection(const cv::Mat& input);
    cv::Mat applyGrayscaleFilter(const cv::Mat& input);
    cv::Mat applyGaussianBlur(const cv::Mat& input);
    
    // Utility methods
    void updateStats(double processingTime);
    std::string formatStats();
};

#endif // OPENCV_PROCESSOR_H
