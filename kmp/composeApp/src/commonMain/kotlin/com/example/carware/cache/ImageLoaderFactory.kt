package com.example.carware.cache

import coil3.ImageLoader
import coil3.PlatformContext

expect fun createImageLoader(context: PlatformContext): ImageLoader
