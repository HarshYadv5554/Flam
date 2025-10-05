package com.flam.edgedetection.jni

import android.util.Log

class JNIBridge {
    
    companion object {
        private const val TAG = "JNIBridge"
        
        init {
            try {
                System.loadLibrary("opencv_java4")
                System.loadLibrary("edgedetection")
                Log.d(TAG, "Native libraries loaded successfully")
            } catch (e: UnsatisfiedLinkError) {
                Log.e(TAG, "Failed to load native libraries", e)
            }
        }
    }
    
    /**
     * Process frame using OpenCV edge detection
     * @param frameData NV21 format frame data
     * @param width Frame width
     * @param height Frame height
     * @return Processed frame data
     */
    external fun processFrame(frameData: ByteArray, width: Int, height: Int): ByteArray
    
    /**
     * Initialize OpenCV processing
     */
    external fun initializeOpenCV(): Boolean
    
    /**
     * Cleanup OpenCV resources
     */
    external fun cleanupOpenCV()
    
    /**
     * Get processing statistics
     */
    external fun getProcessingStats(): String
    
    init {
        initializeOpenCV()
    }
}
