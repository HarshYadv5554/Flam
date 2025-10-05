package com.flam.edgedetection.camera

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(
    private val context: Context,
    private val textureView: android.view.TextureView,
    private val onFrameCaptured: (ImageProxy) -> Unit
) {
    
    private var camera: Camera? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private lateinit var cameraExecutor: ExecutorService
    
    companion object {
        private const val TAG = "CameraManager"
    }
    
    fun startCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder()
                .setTargetResolution(Size(640, 480))
                .build()
                .also {
                    it.setSurfaceProvider(textureView.surfaceProvider)
                }
            
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(640, 480))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, ImageAnalyzer(onFrameCaptured))
                }
            
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    context as LifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
                
                Log.d(TAG, "Camera started successfully")
                
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
            
        }, ContextCompat.getMainExecutor(context))
    }
    
    fun stopCamera() {
        camera?.let {
            // Camera cleanup is handled by ProcessCameraProvider
        }
        imageAnalyzer?.clearAnalyzer()
        cameraExecutor.shutdown()
    }
    
    private class ImageAnalyzer(
        private val onFrameCaptured: (ImageProxy) -> Unit
    ) : ImageAnalysis.Analyzer {
        
        override fun analyze(imageProxy: ImageProxy) {
            onFrameCaptured(imageProxy)
        }
    }
}
