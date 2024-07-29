package com.hd.misaleawianegager.startup

import android.content.Context
import androidx.startup.Initializer

class DependencyGraphInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        // Initialize dependencies
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}