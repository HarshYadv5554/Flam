cmake_minimum_required(VERSION 3.22.1)

project("edgedetection")

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find OpenCV
find_package(OpenCV REQUIRED)

# Include directories
include_directories(${OpenCV_INCLUDE_DIRS})

# Add OpenCV libraries
set(OpenCV_LIBS opencv_core opencv_imgproc opencv_imgcodecs)

# Create the shared library
add_library(edgedetection SHARED
    jni_bridge.cpp
    opencv_processor.cpp
)

# Link OpenCV libraries
target_link_libraries(edgedetection
    ${OpenCV_LIBS}
    android
    log
    GLESv2
)

# Set properties
set_target_properties(edgedetection PROPERTIES
    LIBRARY_OUTPUT_DIRECTORY ${CMAKE_LIBRARY_OUTPUT_DIRECTORY}
)
