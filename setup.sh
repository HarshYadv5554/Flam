#!/bin/bash

# Edge Detection Project Setup Script
echo "🔧 Setting up Edge Detection Project..."

# Check if Android SDK is installed
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ ANDROID_HOME is not set. Please install Android SDK and set ANDROID_HOME environment variable."
    exit 1
fi

# Check if NDK is installed
if [ -z "$ANDROID_NDK_HOME" ]; then
    echo "❌ ANDROID_NDK_HOME is not set. Please install Android NDK and set ANDROID_NDK_HOME environment variable."
    exit 1
fi

echo "✅ Android SDK found at: $ANDROID_HOME"
echo "✅ Android NDK found at: $ANDROID_NDK_HOME"

# Create necessary directories
echo "📁 Creating project directories..."
mkdir -p app/src/main/jniLibs
mkdir -p opencv/src/main/jniLibs
mkdir -p web/public

# Download OpenCV for Android (if not already present)
if [ ! -d "opencv-android-sdk" ]; then
    echo "📥 Downloading OpenCV for Android..."
    echo "Please download OpenCV for Android from https://opencv.org/releases/"
    echo "Extract it to the project root directory as 'opencv-android-sdk'"
    echo "Then run this script again."
    exit 1
fi

# Copy OpenCV libraries
echo "📚 Copying OpenCV libraries..."
cp -r opencv-android-sdk/sdk/native/libs/* app/src/main/jniLibs/
cp -r opencv-android-sdk/sdk/native/libs/* opencv/src/main/jniLibs/

# Setup web dependencies
echo "🌐 Setting up web dependencies..."
cd web
if [ ! -d "node_modules" ]; then
    echo "📦 Installing Node.js dependencies..."
    npm install
fi
cd ..

# Build Android project
echo "🔨 Building Android project..."
./gradlew clean
./gradlew assembleDebug

# Build web project
echo "🌐 Building web project..."
cd web
npm run build
cd ..

echo "✅ Setup complete!"
echo ""
echo "🚀 To run the project:"
echo "1. Open the project in Android Studio"
echo "2. Build and run the Android app"
echo "3. Start the web server: cd web && npm start"
echo "4. Open http://localhost:3000 in your browser"
echo ""
echo "📱 Make sure to:"
echo "- Install OpenCV for Android SDK"
echo "- Set up Android NDK"
echo "- Enable camera permissions on your device"
