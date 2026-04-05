import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import platform.Foundation.NSDocumentationDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDirectory
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun getCacheDirectory(): Path {
    val fileManager = NSFileManager.defaultManager
    val documentsUrl = fileManager.URLForDirectory(
        directory = NSDocumentationDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null
    )
    val path = documentsUrl?.path ?: ""
    return Path(path)
}