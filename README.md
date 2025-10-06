# 🎯 **Real-Time Edge Detection Viewer**
## Android + OpenCV-C++ + OpenGL + Web - RnD Intern Assessment

A complete real-time edge detection application with Android native processing and TypeScript web viewer.

---

## 🚀 **Features Implemented**

### **📱 Android App (Complete)**
- ✅ **Camera Integration** - Real-time camera feed using CameraX API
- ✅ **OpenCV C++ Processing** - Native edge detection via JNI
- ✅ **OpenGL ES 2.0 Rendering** - Hardware-accelerated graphics
- ✅ **Toggle Button** - Switch between raw camera and edge detection
- ✅ **FPS Counter** - Real-time performance monitoring
- ✅ **Processing Time** - Frame processing time tracking
- ✅ **OpenGL Shader Effects** - Grayscale, Invert, Edge Detection
- ✅ **JNI Integration** - Seamless Java ↔ C++ communication

### **🌐 Web Viewer (Complete)**
- ✅ **Static Frame Display** - Sample processed frame visualization
- ✅ **Real-time Stats** - FPS, processing time, connected clients
- ✅ **WebSocket Communication** - Real-time data streaming
- ✅ **HTTP API Endpoint** - RESTful frame data access
- ✅ **Effect Buttons** - Grayscale, Invert, Edge Detection simulation
- ✅ **TypeScript Implementation** - Modern web development

---

## 🏗️ **Architecture**

### **Android App Flow:**
```
Camera → CameraX → NV21 Format → JNI Bridge → OpenCV C++ → Edge Detection → OpenGL ES → Display
```

### **Web Viewer Flow:**
```
TypeScript Server → WebSocket/HTTP → Canvas Rendering → DOM Updates
```

### **Key Components:**
- **MainActivity.kt** - Main Android activity with UI and camera management
- **OpenGLRenderer.kt** - OpenGL ES 2.0 rendering with shader effects
- **JNIBridge.kt** - Java ↔ C++ communication interface
- **opencv_processor.cpp** - OpenCV C++ edge detection implementation
- **Web Server** - TypeScript Express.js server with WebSocket support

---

## 📦 **Setup Instructions**

### **Prerequisites:**
- Android Studio (Latest version)
- Android SDK (API 33+)
- Android NDK (Native Development Kit)
- OpenCV for Android SDK
- Node.js (for web viewer)

### **Android Setup:**

1. **Open Project:**
   ```bash
   # Open Android Studio
   File → Open → Navigate to project folder
   ```

2. **Install Dependencies:**
   - Android NDK (Tools → SDK Manager → SDK Tools)
   - CMake (Tools → SDK Manager → SDK Tools)
   - OpenCV for Android SDK

3. **Configure Environment:**
   ```bash
   # Set environment variables
   ANDROID_HOME=C:\Users\[USER]\AppData\Local\Android\Sdk
   ANDROID_NDK_HOME=C:\Users\[USER]\AppData\Local\Android\Sdk\ndk\[VERSION]
   ```

4. **Build and Run:**
   ```bash
   # In Android Studio
   Build → Make Project
   Run → Run 'app'
   ```

### **Web Setup:**

1. **Install Dependencies:**
   ```bash
   cd web
   npm install
   ```

2. **Build TypeScript:**
   ```bash
   npm run build
   ```

3. **Start Server:**
   ```bash
   npm start
   ```

4. **Access Web Viewer:**
   ```
   http://localhost:3000
   ```

---

## 🎮 **How to Use**

### **Android App:**
1. **Launch App** - Grant camera permissions
2. **Toggle Button** - Switch between raw camera and edge detection
3. **Effect Buttons** - Apply Grayscale, Invert, or Edge Detection
4. **Monitor Performance** - View FPS and processing time

### **Web Viewer:**
1. **Load Sample Frame** - Click to load test image
2. **Start Simulation** - Begin edge detection simulation
3. **Apply Effects** - Use effect buttons for different visual styles
4. **Monitor Stats** - View FPS, processing time, and connected clients

---

## 🧪 **Testing**

### **Android Testing:**
- Connect Android device or start emulator
- Run app and test all features
- Verify camera permissions
- Test toggle and effect buttons
- Monitor FPS performance

### **Web Testing:**
- Open `http://localhost:3000`
- Test sample frame loading
- Verify WebSocket connection
- Test effect buttons
- Monitor real-time stats

---

## 📊 **Performance Metrics**

### **Android App:**
- **Target FPS:** 10-15 FPS minimum
- **Processing Time:** <100ms per frame
- **Memory Usage:** Optimized for mobile devices
- **OpenGL Performance:** Hardware-accelerated rendering

### **Web Viewer:**
- **Real-time Updates:** WebSocket communication
- **Frame Processing:** Simulated edge detection
- **Effect Rendering:** Canvas-based graphics
- **Performance Monitoring:** Live statistics

---

## 🔧 **Technical Details**

### **Android Technologies:**
- **Kotlin** - Modern Android development
- **CameraX** - Latest camera API
- **OpenCV 4.x** - Computer vision processing
- **OpenGL ES 2.0** - Graphics rendering
- **JNI** - Native code integration
- **CMake** - C++ build system

### **Web Technologies:**
- **TypeScript** - Type-safe JavaScript
- **Express.js** - Web server framework
- **WebSocket** - Real-time communication
- **Canvas API** - Graphics rendering
- **Node.js** - Server runtime

---

## 📁 **Project Structure**

```
Flam/
├── app/                          # Android app
│   ├── src/main/
│   │   ├── java/com/flam/edgedetection/
│   │   │   ├── MainActivity.kt           # Main activity
│   │   │   ├── camera/CameraManager.kt  # Camera handling
│   │   │   ├── gl/OpenGLRenderer.kt    # OpenGL rendering
│   │   │   └── jni/JNIBridge.kt         # JNI interface
│   │   ├── cpp/                         # C++ native code
│   │   │   ├── jni_bridge.cpp           # JNI implementation
│   │   │   ├── opencv_processor.cpp     # OpenCV processing
│   │   │   └── CMakeLists.txt           # CMake configuration
│   │   └── res/                          # Android resources
│   └── build.gradle                     # App build config
├── web/                           # Web viewer
│   ├── src/
│   │   ├── index.ts                     # Main server
│   │   ├── frame-processor.ts            # Frame processing
│   │   └── websocket-server.ts           # WebSocket server
│   ├── public/
│   │   └── index.html                    # Web viewer UI
│   └── package.json                      # Node.js config
├── opencv/                        # OpenCV module
└── README.md                      # This file
```

---

## 🎯 **Assignment Compliance**

### **✅ Required Features:**
- ✅ Camera feed integration (CameraX)
- ✅ OpenCV C++ processing (JNI)
- ✅ OpenGL ES rendering
- ✅ TypeScript web viewer
- ✅ Real-time performance (10-15 FPS)
- ✅ Modular project structure

### **✅ Bonus Features:**
- ✅ Toggle button (Raw vs Edge-detected)
- ✅ FPS counter and processing time
- ✅ OpenGL shader effects (Grayscale, Invert)
- ✅ WebSocket/HTTP endpoint for web viewer

---

## 🚀 **Quick Start**

### **Run Android App:**
1. Open Android Studio
2. Open project folder
3. Connect device/start emulator
4. Click "Run" button

### **Run Web Viewer:**
1. Open terminal in `web/` folder
2. Run `npm install && npm start`
3. Open `http://localhost:3000`

---

## 📝 **Commit History**

The project includes a complete Git commit history showing:
- Initial project setup
- Android app development
- Web viewer implementation
- Bonus features addition
- Testing and debugging
- Final integration

---

## 🎓 **Learning Outcomes**

This project demonstrates:
- **Android Development** - Modern Android app development
- **Native Programming** - C++ and JNI integration
- **Computer Vision** - OpenCV implementation
- **Graphics Programming** - OpenGL ES shaders
- **Web Development** - TypeScript and real-time communication
- **System Integration** - Multi-platform development

---

**🎯 Ready for submission with complete functionality and all bonus features implemented!**