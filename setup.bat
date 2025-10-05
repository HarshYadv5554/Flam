@echo off
REM Edge Detection Project Setup Script for Windows
echo 🔧 Setting up Edge Detection Project...

REM Check if Android SDK is installed
if "%ANDROID_HOME%"=="" (
    echo ❌ ANDROID_HOME is not set. Please install Android SDK and set ANDROID_HOME environment variable.
    pause
    exit /b 1
)

REM Check if NDK is installed
if "%ANDROID_NDK_HOME%"=="" (
    echo ❌ ANDROID_NDK_HOME is not set. Please install Android NDK and set ANDROID_NDK_HOME environment variable.
    pause
    exit /b 1
)

echo ✅ Android SDK found at: %ANDROID_HOME%
echo ✅ Android NDK found at: %ANDROID_NDK_HOME%

REM Create necessary directories
echo 📁 Creating project directories...
if not exist "app\src\main\jniLibs" mkdir "app\src\main\jniLibs"
if not exist "opencv\src\main\jniLibs" mkdir "opencv\src\main\jniLibs"
if not exist "web\public" mkdir "web\public"

REM Check for OpenCV
if not exist "opencv-android-sdk" (
    echo 📥 OpenCV for Android not found.
    echo Please download OpenCV for Android from https://opencv.org/releases/
    echo Extract it to the project root directory as 'opencv-android-sdk'
    echo Then run this script again.
    pause
    exit /b 1
)

REM Copy OpenCV libraries
echo 📚 Copying OpenCV libraries...
xcopy /E /I "opencv-android-sdk\sdk\native\libs\*" "app\src\main\jniLibs\"
xcopy /E /I "opencv-android-sdk\sdk\native\libs\*" "opencv\src\main\jniLibs\"

REM Setup web dependencies
echo 🌐 Setting up web dependencies...
cd web
if not exist "node_modules" (
    echo 📦 Installing Node.js dependencies...
    npm install
)
cd ..

REM Build Android project
echo 🔨 Building Android project...
gradlew.bat clean
gradlew.bat assembleDebug

REM Build web project
echo 🌐 Building web project...
cd web
npm run build
cd ..

echo ✅ Setup complete!
echo.
echo 🚀 To run the project:
echo 1. Open the project in Android Studio
echo 2. Build and run the Android app
echo 3. Start the web server: cd web ^&^& npm start
echo 4. Open http://localhost:3000 in your browser
echo.
echo 📱 Make sure to:
echo - Install OpenCV for Android SDK
echo - Set up Android NDK
echo - Enable camera permissions on your device
pause
