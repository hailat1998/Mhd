
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id ("org.jetbrains.kotlin.kapt") version "1.9.0" apply false
    kotlin("plugin.serialization") version "1.9.23"
    alias(libs.plugins.compose.compiler) apply false
}