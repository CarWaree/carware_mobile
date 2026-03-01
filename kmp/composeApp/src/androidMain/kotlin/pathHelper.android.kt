import android.content.Context
import kotlinx.io.files.Path

lateinit var androidAppContext: Context

actual fun getCacheDirectory(): Path {
    val cacheDir = androidAppContext.cacheDir
    return Path(cacheDir.absolutePath)
}