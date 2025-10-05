#include <jni.h>
#include <android/log.h>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/imgcodecs.hpp>
#include "opencv_processor.h"

#define LOG_TAG "EdgeDetectionJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static OpenCVProcessor* processor = nullptr;

extern "C" {

JNIEXPORT jbyteArray JNICALL
Java_com_flam_edgedetection_jni_JNIBridge_processFrame(
    JNIEnv *env, jobject thiz, jbyteArray frameData, jint width, jint height) {
    
    if (processor == nullptr) {
        LOGE("Processor not initialized");
        return nullptr;
    }
    
    // Get frame data
    jbyte* frameBytes = env->GetByteArrayElements(frameData, nullptr);
    jsize frameSize = env->GetArrayLength(frameData);
    
    // Convert to OpenCV Mat
    cv::Mat yuvMat(height + height/2, width, CV_8UC1, frameBytes);
    cv::Mat rgbMat;
    cv::cvtColor(yuvMat, rgbMat, cv::COLOR_YUV2RGB_NV21);
    
    // Process frame
    cv::Mat processedMat = processor->processFrame(rgbMat);
    
    // Convert back to byte array
    std::vector<uchar> buffer;
    cv::imencode(".jpg", processedMat, buffer);
    
    // Create Java byte array
    jbyteArray result = env->NewByteArray(buffer.size());
    env->SetByteArrayRegion(result, 0, buffer.size(), reinterpret_cast<const jbyte*>(buffer.data()));
    
    // Release resources
    env->ReleaseByteArrayElements(frameData, frameBytes, JNI_ABORT);
    
    return result;
}

JNIEXPORT jboolean JNICALL
Java_com_flam_edgedetection_jni_JNIBridge_initializeOpenCV(JNIEnv *env, jobject thiz) {
    LOGI("Initializing OpenCV processor");
    
    if (processor != nullptr) {
        delete processor;
    }
    
    processor = new OpenCVProcessor();
    return processor != nullptr;
}

JNIEXPORT void JNICALL
Java_com_flam_edgedetection_jni_JNIBridge_cleanupOpenCV(JNIEnv *env, jobject thiz) {
    LOGI("Cleaning up OpenCV processor");
    
    if (processor != nullptr) {
        delete processor;
        processor = nullptr;
    }
}

JNIEXPORT jstring JNICALL
Java_com_flam_edgedetection_jni_JNIBridge_getProcessingStats(JNIEnv *env, jobject thiz) {
    if (processor == nullptr) {
        return env->NewStringUTF("Processor not initialized");
    }
    
    std::string stats = processor->getStats();
    return env->NewStringUTF(stats.c_str());
}

} // extern "C"
