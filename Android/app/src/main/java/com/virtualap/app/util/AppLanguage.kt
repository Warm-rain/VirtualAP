package com.virtualap.app.util

import android.content.Context
import android.content.res.Configuration
import com.virtualap.app.R
import java.util.Locale

/**
 * In-app language selection. The chosen value lives in SharedPreferences;
 * both the Application and the Activity wrap their base context through
 * [wrap] (from attachBaseContext), which is all a pure-Compose
 * ComponentActivity app needs — no AppCompat locale delegation. A language
 * change takes effect by persisting the new value and recreating the
 * activity, so attachBaseContext re-wraps with the fresh preference.
 */
enum class AppLanguage(val prefValue: String, val displayNameRes: Int) {
    SYSTEM("system", R.string.language_system),
    ENGLISH("en", R.string.language_en),
    CHINESE("zh", R.string.language_zh);

    companion object {
        fun fromPref(pref: String): AppLanguage =
            entries.firstOrNull { it.prefValue == pref } ?: SYSTEM

        /** Wrap [context] so its resources resolve against [language]. */
        fun wrap(context: Context, language: AppLanguage): Context {
            if (language == SYSTEM) return context
            val locale = Locale.forLanguageTag(language.prefValue)
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration).apply {
                setLocales(android.os.LocaleList(locale))
            }
            return context.createConfigurationContext(config)
        }
    }
}
