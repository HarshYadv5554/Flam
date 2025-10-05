# Edge Detection Web Viewer

A TypeScript-based web viewer for displaying processed frames from the Android edge detection application.

## Features

- Real-time frame display
- WebSocket communication
- Performance statistics
- Sample frame generation
- Responsive design

## Setup

1. Install dependencies:
   ```bash
   npm install
   ```

2. Build the project:
   ```bash
   npm run build
   ```

3. Start the server:
   ```bash
   npm start
   ```

4. Open http://localhost:3000 in your browser

## Development

- `npm run dev`: Start development server with hot reload
- `npm run watch`: Watch for changes and rebuild
- `npm run build`: Build production bundle

## API Endpoints

- `GET /`: Main web interface
- `GET /api/stats`: Get processing statistics
- `POST /api/frame`: Submit frame for processing
- `GET /api/sample-frame`: Get sample frame for testing

## WebSocket Events

- `frame`: New processed frame
- `stats`: Updated statistics
- `error`: Error messages
