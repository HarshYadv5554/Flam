@echo off
echo 🔍 Testing Edge Detection App Setup...
echo.

echo 📱 Checking Android Development Environment:
if exist "%ANDROID_HOME%" (
    echo ✅ ANDROID_HOME: %ANDROID_HOME%
) else (
    echo ❌ ANDROID_HOME not set. Please install Android SDK.
)

if exist "%ANDROID_NDK_HOME%" (
    echo ✅ ANDROID_NDK_HOME: %ANDROID_NDK_HOME%
) else (
    echo ❌ ANDROID_NDK_HOME not set. Please install Android NDK.
)

echo.
echo 🌐 Checking Web Development Environment:
where node >nul 2>&1
if %errorlevel%==0 (
    echo ✅ Node.js found
    node --version
) else (
    echo ❌ Node.js not found. Please install Node.js.
)

where npm >nul 2>&1
if %errorlevel%==0 (
    echo ✅ npm found
    npm --version
) else (
    echo ❌ npm not found.
)

echo.
echo 📚 Checking Project Structure:
if exist "app\build.gradle" (
    echo ✅ Android app structure found
) else (
    echo ❌ Android app structure missing
)

if exist "web\package.json" (
    echo ✅ Web viewer structure found
) else (
    echo ❌ Web viewer structure missing
)

if exist "opencv-android-sdk" (
    echo ✅ OpenCV SDK found
) else (
    echo ❌ OpenCV SDK missing. Please download and extract OpenCV.
)

echo.
echo 🚀 Next Steps:
echo 1. Install missing dependencies above
echo 2. Download OpenCV for Android from https://opencv.org/releases/
echo 3. Extract OpenCV to project root as 'opencv-android-sdk'
echo 4. Open project in Android Studio
echo 5. Run setup.bat to configure libraries
echo 6. Build and test the application

pause
