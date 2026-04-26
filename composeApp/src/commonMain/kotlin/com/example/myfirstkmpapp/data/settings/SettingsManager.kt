package com.example.myfirstkmpapp.data.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class SettingsManager(
    private val settings: Settings
) {
    var theme: String
        get() = settings.getString("theme", "system")
        set(value) {
            settings.putString("theme", value)
        }

    var sortOrder: String
        get() = settings.getString("sort_order", "newest")
        set(value) {
            settings.putString("sort_order", value)
        }
}