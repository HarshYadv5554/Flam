# 🚀 Complete Setup Guide for Edge Detection App

## Prerequisites Installation

### 1. Android Studio Setup
1. **Download Android Studio**: https://developer.android.com/studio
2. **Install Android SDK**: 
   - Open Android Studio
   - Go to Tools → SDK Manager
   - Install Android SDK Platform 34
   - Install Android SDK Build-Tools
   - Install Android NDK (Side by side)

### 2. OpenCV Setup
1. **Download OpenCV for Android**: https://opencv.org/releases/
2. **Extract to project root**: Create folder `opencv-android-sdk`
3. **Copy libraries**: Run `setup.bat` (Windows) or `setup.sh` (Linux/Mac)

### 3. Node.js Setup (for Web Viewer)
1. **Download Node.js**: https://nodejs.org/
2. **Install dependencies**: `cd web && npm install`

## Testing Steps

### Phase 1: Android App Testing
1. **Open in Android Studio**:
   - File → Open → Select Flam folder
   - Wait for Gradle sync
   - Fix any dependency issues

2. **Build the project**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Run on device/emulator**:
   - Connect Android device with USB debugging
   - Or create AVD (Android Virtual Device)
   - Click Run button in Android Studio

### Phase 2: Web Viewer Testing
1. **Start web server**:
   ```bash
   cd web
   npm install
   npm run build
   npm start
   ```

2. **Open browser**: http://localhost:3000

3. **Test features**:
   - Load sample frame
   - Start simulation
   - Check WebSocket connection

## Expected Results

### Android App:
- ✅ Camera preview working
- ✅ Edge detection toggle working
- ✅ FPS counter showing
- ✅ Real-time processing

### Web Viewer:
- ✅ WebSocket connection established
- ✅ Frame display working
- ✅ Performance stats updating
- ✅ Responsive design

## Troubleshooting

### Common Issues:
1. **NDK not found**: Set ANDROID_NDK_HOME environment variable
2. **OpenCV missing**: Download and extract OpenCV SDK
3. **Camera permissions**: Enable in device settings
4. **WebSocket errors**: Check firewall settings

### Debug Commands:
```bash
# Check Android SDK
echo $ANDROID_HOME

# Check NDK
echo $ANDROID_NDK_HOME

# Check Node.js
node --version
npm --version

# Build Android
./gradlew clean assembleDebug

# Test web
cd web && npm test
```
