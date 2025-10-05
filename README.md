# Real-Time Edge Detection Viewer

A comprehensive Android application that captures camera frames, processes them using OpenCV in C++ (via JNI), and displays the processed output using OpenGL ES. Includes a TypeScript web viewer for displaying processed frames.

## 🚀 Features Implemented

### Android App
- 📸 Real-time camera feed capture using TextureView
- 🔁 Frame processing via OpenCV C++ with JNI bridge
- 🎨 OpenGL ES 2.0 rendering for processed frames
- ⚡ Real-time edge detection with Canny algorithm
- 📊 FPS counter and performance monitoring
- 🔄 Toggle between raw camera feed and edge-detected output

### Web Viewer (TypeScript)
- 🌐 TypeScript-based web page for displaying processed frames
- 📷 Static sample processed frame display
- 📈 Frame statistics overlay (FPS, resolution)
- 🎨 Clean, modular TypeScript implementation

## 📱 Screenshots

*Screenshots will be added after implementation*

## ⚙️ Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android NDK (Native Development Kit)
- OpenCV for Android
- Node.js and npm (for TypeScript web viewer)
- Git for version control

### Android Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd Flam
   ```

2. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Configure NDK:**
   - Go to File → Project Structure → SDK Location
   - Set Android NDK location
   - Ensure NDK version is compatible with OpenCV

4. **OpenCV Setup:**
   - Download OpenCV for Android from [opencv.org](https://opencv.org/releases/)
   - Extract to a local directory
   - In Android Studio, go to File → New → Import Module
   - Select the OpenCV Android SDK
   - Add OpenCV as a dependency in your app's build.gradle

5. **Build and Run:**
   ```bash
   ./gradlew assembleDebug
   ```

### Web Viewer Setup

1. **Navigate to web directory:**
   ```bash
   cd web
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Build TypeScript:**
   ```bash
   npm run build
   ```

4. **Start development server:**
   ```bash
   npm start
   ```

## 🧠 Architecture Overview

### Project Structure
```
Flam/
├── app/                    # Android application code
│   ├── src/main/java/     # Java/Kotlin source code
│   ├── src/main/cpp/      # C++ native code
│   └── src/main/assets/   # Assets and resources
├── jni/                   # JNI bridge implementation
├── gl/                    # OpenGL renderer classes
├── web/                   # TypeScript web viewer
│   ├── src/              # TypeScript source
│   ├── dist/             # Compiled JavaScript
│   └── public/           # Static assets
└── README.md
```

### Frame Processing Flow

1. **Camera Capture**: Android camera captures frames using TextureView
2. **JNI Bridge**: Frames are passed to native C++ code via JNI
3. **OpenCV Processing**: C++ code applies Canny edge detection using OpenCV
4. **OpenGL Rendering**: Processed frames are rendered using OpenGL ES 2.0
5. **Web Integration**: Processed frames can be exported to web viewer

### Key Components

- **CameraManager**: Handles camera initialization and frame capture
- **JNIBridge**: Manages communication between Java and C++
- **OpenCVProcessor**: C++ class for image processing operations
- **OpenGLRenderer**: Handles OpenGL ES rendering pipeline
- **WebViewer**: TypeScript-based web interface for frame display

## 🔧 Technical Implementation

### JNI Integration
- Native C++ functions exposed to Java via JNI
- Efficient memory management for image data
- Thread-safe frame processing

### OpenCV Usage
- Canny edge detection algorithm
- Grayscale conversion
- Real-time processing optimization

### OpenGL ES 2.0
- Texture-based rendering
- Shader programs for image effects
- Efficient GPU utilization

### TypeScript Web Viewer
- Modular component architecture
- Real-time frame display
- Performance monitoring

## 📊 Performance Metrics

- Target FPS: 15-30 FPS
- Processing latency: <100ms
- Memory usage: Optimized for mobile devices

## 🚀 Getting Started

1. Follow the setup instructions above
2. Build and run the Android application
3. Start the web viewer for additional frame analysis
4. Test edge detection with various lighting conditions

## 📝 Development Notes

- All commits follow conventional commit format
- Modular development approach with clear separation of concerns
- Comprehensive error handling and logging
- Performance optimization for real-time processing

## 🤝 Contributing

This is an assessment project. Please refer to the assignment requirements for submission guidelines.

## 📄 License

This project is created for educational and assessment purposes.
