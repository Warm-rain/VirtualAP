package com.virtualap.app

import android.app.Application
import android.content.Context
import com.topjohnwu.superuser.Shell
import com.virtualap.app.util.AppLanguage
import com.virtualap.app.util.Backend
import com.virtualap.app.util.PreferencesManager

class VirtualAPApplication : Application() {
    private lateinit var appLanguage: AppLanguage

    override fun attachBaseContext(base: Context) {
        appLanguage = AppLanguage.fromPref(PreferencesManager.readLanguageRaw(base))
        super.attachBaseContext(AppLanguage.wrap(base, appLanguage))
    }

    override fun onCreate() {
        super.onCreate()
        Shell.enableVerboseLogging = false
        Shell.setDefaultBuilder(
            Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(30)
        )
        // Synchronous: everything that shells out depends on these paths.
        Backend.install(this)
    }
}
