package com.flam.edgedetection.gl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val textureView: android.view.TextureView) : GLSurfaceView.Renderer {
    
    companion object {
        private const val TAG = "OpenGLRenderer"
        
        // Vertex shader code
        private const val vertexShaderCode = """
            uniform mat4 uMVPMatrix;
            attribute vec4 vPosition;
            attribute vec2 vTexCoord;
            varying vec2 vTexCoordVarying;
            void main() {
                gl_Position = uMVPMatrix * vPosition;
                vTexCoordVarying = vTexCoord;
            }
        """
        
        // Fragment shader code
        private const val fragmentShaderCode = """
            precision mediump float;
            uniform sampler2D uTexture;
            varying vec2 vTexCoordVarying;
            void main() {
                gl_FragColor = texture2D(uTexture, vTexCoordVarying);
            }
        """
    }
    
    private var program: Int = 0
    private var textureHandle: Int = 0
    private var mvpMatrixHandle: Int = 0
    private var positionHandle: Int = 0
    private var texCoordHandle: Int = 0
    private var textureHandleUniform: Int = 0
    
    private val mvpMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    
    private var currentFrameData: ByteArray? = null
    private var frameWidth = 0
    private var frameHeight = 0
    
    // Quad vertices (full screen)
    private val quadVertices = floatArrayOf(
        -1.0f, -1.0f, 0.0f,  // Bottom left
         1.0f, -1.0f, 0.0f,  // Bottom right
        -1.0f,  1.0f, 0.0f,  // Top left
         1.0f,  1.0f, 0.0f   // Top right
    )
    
    // Texture coordinates
    private val quadTexCoords = floatArrayOf(
        0.0f, 1.0f,  // Bottom left
        1.0f, 1.0f,  // Bottom right
        0.0f, 0.0f,  // Top left
        1.0f, 0.0f   // Top right
    )
    
    private var vertexBuffer: FloatBuffer? = null
    private var texCoordBuffer: FloatBuffer? = null
    
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "Surface created")
        
        // Set background color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        
        // Initialize shaders
        initializeShaders()
        
        // Initialize buffers
        initializeBuffers()
        
        // Initialize matrices
        Matrix.setIdentityM(mvpMatrix, 0)
        Matrix.setIdentityM(projectionMatrix, 0)
        Matrix.setIdentityM(viewMatrix, 0)
    }
    
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "Surface changed: $width x $height")
        
        GLES20.glViewport(0, 0, width, height)
        
        // Create orthographic projection matrix
        Matrix.orthoM(projectionMatrix, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
    }
    
    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        
        if (currentFrameData != null && frameWidth > 0 && frameHeight > 0) {
            renderFrame()
        }
    }
    
    fun initialize() {
        // Initialize OpenGL context
        Log.d(TAG, "OpenGL renderer initialized")
    }
    
    fun renderFrame(frameData: ByteArray, width: Int, height: Int) {
        currentFrameData = frameData
        frameWidth = width
        frameHeight = height
    }
    
    private fun renderFrame() {
        if (program == 0) return
        
        // Use the shader program
        GLES20.glUseProgram(program)
        
        // Set up vertex data
        vertexBuffer?.let { buffer ->
            buffer.position(0)
            GLES20.glVertexAttribPointer(
                positionHandle, 3, GLES20.GL_FLOAT, false, 0, buffer
            )
            GLES20.glEnableVertexAttribArray(positionHandle)
        }
        
        // Set up texture coordinates
        texCoordBuffer?.let { buffer ->
            buffer.position(0)
            GLES20.glVertexAttribPointer(
                texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, buffer
            )
            GLES20.glEnableVertexAttribArray(texCoordHandle)
        }
        
        // Set up texture
        setupTexture()
        
        // Set MVP matrix
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        
        // Draw the quad
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        
        // Disable vertex arrays
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(texCoordHandle)
    }
    
    private fun setupTexture() {
        if (textureHandle == 0) {
            val textures = IntArray(1)
            GLES20.glGenTextures(1, textures, 0)
            textureHandle = textures[0]
        }
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle)
        
        // Set texture parameters
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        
        // Upload texture data
        currentFrameData?.let { data ->
            val buffer = ByteBuffer.wrap(data)
            GLES20.glTexImage2D(
                GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE,
                frameWidth, frameHeight, 0, GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, buffer
            )
        }
        
        // Set texture uniform
        GLES20.glUniform1i(textureHandleUniform, 0)
    }
    
    private fun initializeShaders() {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        
        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)
        
        // Get handles
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        texCoordHandle = GLES20.glGetAttribLocation(program, "vTexCoord")
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        textureHandleUniform = GLES20.glGetUniformLocation(program, "uTexture")
        
        Log.d(TAG, "Shaders initialized")
    }
    
    private fun initializeBuffers() {
        // Initialize vertex buffer
        val vertexByteBuffer = ByteBuffer.allocateDirect(quadVertices.size * 4)
        vertexByteBuffer.order(ByteOrder.nativeOrder())
        vertexBuffer = vertexByteBuffer.asFloatBuffer()
        vertexBuffer?.put(quadVertices)
        vertexBuffer?.position(0)
        
        // Initialize texture coordinate buffer
        val texCoordByteBuffer = ByteBuffer.allocateDirect(quadTexCoords.size * 4)
        texCoordByteBuffer.order(ByteOrder.nativeOrder())
        texCoordBuffer = texCoordByteBuffer.asFloatBuffer()
        texCoordBuffer?.put(quadTexCoords)
        texCoordBuffer?.position(0)
    }
    
    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        
        val compileStatus = IntArray(1)
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        
        if (compileStatus[0] == 0) {
            val error = GLES20.glGetShaderInfoLog(shader)
            Log.e(TAG, "Shader compilation failed: $error")
            GLES20.glDeleteShader(shader)
            return 0
        }
        
        return shader
    }
    
    fun cleanup() {
        if (textureHandle != 0) {
            GLES20.glDeleteTextures(1, intArrayOf(textureHandle), 0)
            textureHandle = 0
        }
        
        if (program != 0) {
            GLES20.glDeleteProgram(program)
            program = 0
        }
    }
}
