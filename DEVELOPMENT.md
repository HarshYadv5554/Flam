# Development Guide

## 🚀 Quick Start

### Prerequisites
- Android Studio (latest version)
- Android SDK (API level 24+)
- Android NDK (latest version)
- OpenCV for Android SDK
- Node.js and npm (for web viewer)
- Git for version control

### Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd Flam
   ```

2. **Download OpenCV for Android:**
   - Visit [OpenCV Releases](https://opencv.org/releases/)
   - Download OpenCV for Android
   - Extract to project root as `opencv-android-sdk`

3. **Run setup script:**
   ```bash
   # Linux/Mac
   chmod +x setup.sh
   ./setup.sh
   
   # Windows
   setup.bat
   ```

4. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

## 🏗️ Project Architecture

### Directory Structure
```
Flam/
├── app/                    # Android application
│   ├── src/main/java/     # Java/Kotlin source
│   ├── src/main/cpp/      # C++ native code
│   └── src/main/res/      # Android resources
├── opencv/                # OpenCV module
├── web/                   # TypeScript web viewer
│   ├── src/              # TypeScript source
│   ├── dist/             # Compiled JavaScript
│   └── public/           # Static assets
└── README.md
```

### Key Components

#### Android App
- **MainActivity.kt**: Main activity with camera and OpenGL integration
- **CameraManager.kt**: Handles camera initialization and frame capture
- **JNIBridge.kt**: JNI interface for C++ communication
- **OpenGLRenderer.kt**: OpenGL ES 2.0 rendering pipeline

#### Native C++ Code
- **jni_bridge.cpp**: JNI implementation for Java-C++ communication
- **opencv_processor.h/cpp**: OpenCV edge detection processing
- **CMakeLists.txt**: CMake configuration for native build

#### Web Viewer
- **index.ts**: Express.js server with WebSocket support
- **frame-processor.ts**: TypeScript frame processing logic
- **websocket-server.ts**: WebSocket server for real-time communication
- **index.html**: Web interface for frame display

## 🔧 Development Workflow

### Android Development

1. **Camera Integration:**
   - Uses CameraX API for modern camera access
   - TextureView for camera preview
   - Real-time frame capture and processing

2. **JNI Integration:**
   - Native C++ functions exposed to Java
   - Efficient memory management
   - Thread-safe frame processing

3. **OpenGL Rendering:**
   - OpenGL ES 2.0 for hardware acceleration
   - Shader-based rendering
   - Texture-based frame display

### Web Development

1. **TypeScript Setup:**
   ```bash
   cd web
   npm install
   npm run build
   npm start
   ```

2. **WebSocket Communication:**
   - Real-time frame broadcasting
   - Performance statistics
   - Error handling

3. **Frame Processing:**
   - Base64 image encoding/decoding
   - Edge detection simulation
   - Performance monitoring

## 🧪 Testing

### Android Testing
1. Build and install the APK
2. Test camera permissions
3. Verify edge detection functionality
4. Check OpenGL rendering performance

### Web Testing
1. Start the web server
2. Open browser to localhost:3000
3. Test WebSocket connection
4. Verify frame display

## 📊 Performance Optimization

### Android Optimizations
- Efficient memory management in JNI
- OpenGL texture caching
- Camera frame rate optimization
- Native code optimization

### Web Optimizations
- WebSocket connection pooling
- Image compression
- Client-side caching
- Performance monitoring

## 🐛 Debugging

### Android Debugging
- Use Android Studio debugger
- Check logcat for errors
- Profile memory usage
- Monitor frame rates

### Web Debugging
- Browser developer tools
- WebSocket connection monitoring
- Performance profiling
- Error logging

## 📝 Git Workflow

### Commit Guidelines
- Use conventional commit format
- Include meaningful commit messages
- Test before committing
- Keep commits atomic

### Branch Strategy
- `main`: Production-ready code
- `develop`: Development branch
- `feature/*`: Feature branches
- `hotfix/*`: Bug fixes

## 🚀 Deployment

### Android Deployment
1. Build release APK
2. Sign with release key
3. Test on target devices
4. Upload to app store

### Web Deployment
1. Build production bundle
2. Deploy to web server
3. Configure HTTPS
4. Set up monitoring

## 📚 Additional Resources

- [Android CameraX Documentation](https://developer.android.com/training/camerax)
- [OpenCV Android Documentation](https://docs.opencv.org/4.x/d9/df8/tutorial_root.html)
- [OpenGL ES 2.0 Guide](https://developer.android.com/guide/topics/graphics/opengl)
- [TypeScript Documentation](https://www.typescriptlang.org/docs/)
- [WebSocket API](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is created for educational and assessment purposes.
