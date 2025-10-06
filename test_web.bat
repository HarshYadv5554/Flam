@echo off
echo 🌐 Testing Web Viewer...
echo.

cd web

echo 📦 Installing dependencies...
npm install

echo 🔨 Building TypeScript...
npm run build

if %errorlevel% neq 0 (
    echo ❌ Build failed!
    pause
    exit /b 1
)

echo ✅ Build successful!

echo 🚀 Starting web server...
echo Open http://localhost:3000 in your browser
echo Press Ctrl+C to stop the server
echo.

npm start
