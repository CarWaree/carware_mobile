import platform.Foundation.NSUserDefaults

class IOSUserSession : UserSession {
    private val defaults = NSUserDefaults.standardUserDefaults()

    override suspend fun login(token: String?) {
        defaults.setBool(true, "is_logged_in")
        defaults.setObject(token, "token")
    }

    override suspend fun logout() {
        defaults.removeObjectForKey("is_logged_in")
        defaults.removeObjectForKey("token")
    }

    override fun isLoggedIn(): Boolean {
        return defaults.boolForKey("is_logged_in")
    }

    override suspend fun getToken(): String? {
        return defaults.stringForKey("token")
    }

    // Singleton object for easy access
    companion object Manager {
        private val session = IOSUserSession()

        suspend fun login(token: String) = session.login(token)
        suspend fun logout() = session.logout()
        fun isLoggedIn(): Boolean = session.isLoggedIn()
        suspend fun getToken(): String? = session.getToken()
    }
}
