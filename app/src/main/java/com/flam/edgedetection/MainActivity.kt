package com.flam.edgedetection

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.flam.edgedetection.camera.CameraManager
import com.flam.edgedetection.gl.OpenGLRenderer
import com.flam.edgedetection.jni.JNIBridge
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    
    private lateinit var cameraManager: CameraManager
    private lateinit var openGLRenderer: OpenGLRenderer
    private lateinit var jniBridge: JNIBridge
    private lateinit var cameraExecutor: ExecutorService
    
    private lateinit var textureView: TextureView
    private lateinit var fpsTextView: TextView
    private lateinit var toggleButton: Button
    
    private var isEdgeDetectionEnabled = true
    private var frameCount = 0
    private var lastTime = System.currentTimeMillis()
    
    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeViews()
        initializeComponents()
        setupClickListeners()
        requestPermissions()
    }
    
    private fun initializeViews() {
        textureView = findViewById(R.id.textureView)
        fpsTextView = findViewById(R.id.fpsTextView)
        toggleButton = findViewById(R.id.toggleButton)
    }
    
    private fun initializeComponents() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraManager = CameraManager(this, textureView, ::onFrameCaptured)
        openGLRenderer = OpenGLRenderer(textureView)
        jniBridge = JNIBridge()
        
        // Initialize OpenGL renderer
        openGLRenderer.initialize()
    }
    
    private fun setupClickListeners() {
        toggleButton.setOnClickListener {
            isEdgeDetectionEnabled = !isEdgeDetectionEnabled
            toggleButton.text = if (isEdgeDetectionEnabled) "Show Raw" else "Show Edge"
        }
    }
    
    private fun onFrameCaptured(imageProxy: ImageProxy) {
        try {
            val yBuffer = imageProxy.planes[0].buffer
            val uBuffer = imageProxy.planes[1].buffer
            val vBuffer = imageProxy.planes[2].buffer
            
            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()
            
            val nv21 = ByteArray(ySize + uSize + vSize)
            yBuffer.get(nv21, 0, ySize)
            vBuffer.get(nv21, ySize, vSize)
            uBuffer.get(nv21, ySize + vSize, uSize)
            
            val width = imageProxy.width
            val height = imageProxy.height
            
            // Process frame through JNI
            val processedFrame = if (isEdgeDetectionEnabled) {
                jniBridge.processFrame(nv21, width, height)
            } else {
                nv21 // Return original frame
            }
            
            // Render with OpenGL
            openGLRenderer.renderFrame(processedFrame, width, height)
            
            // Update FPS counter
            updateFPS()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing frame", e)
        } finally {
            imageProxy.close()
        }
    }
    
    private fun updateFPS() {
        frameCount++
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime >= 1000) {
            val fps = frameCount * 1000 / (currentTime - lastTime)
            runOnUiThread {
                fpsTextView.text = "FPS: $fps"
            }
            frameCount = 0
            lastTime = currentTime
        }
    }
    
    private fun requestPermissions() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }
    
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun startCamera() {
        cameraManager.startCamera()
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Log.e(TAG, "Permissions not granted by the user.")
                finish()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        openGLRenderer.cleanup()
    }
}
