@echo off
echo 🌐 Testing Web Viewer...
echo.

cd web

echo 📦 Checking dependencies...
if not exist "node_modules" (
    echo Installing dependencies...
    npm install
)

echo 🔨 Building project...
npm run build

if %errorlevel% neq 0 (
    echo ❌ Build failed!
    pause
    exit /b 1
)

echo ✅ Build successful!

echo 🚀 Starting web server...
echo.
echo 📱 Open your browser and go to: http://localhost:3000
echo.
echo 🧪 Test Features:
echo - Load sample frame
echo - Start simulation
echo - Check WebSocket connection
echo - View performance stats
echo.
echo Press Ctrl+C to stop the server
echo.

node dist/index.js
