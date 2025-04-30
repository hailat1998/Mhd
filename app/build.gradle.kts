plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlinx-serialization")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.hd.misaleawianegager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hd.misaleawianegager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.12"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.startup.runtime)

    /* *****************************************************
     **** Lifecycle
     ****************************************************** */
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.process)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.runtime.livedata)
    implementation(libs.activity.ktx)


    /* *****************************************************
      **** Coroutines
      ****************************************************** */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    /* *****************************************************
      **** Splash
      ****************************************************** */
    implementation(libs.core.splashscreen)

    /* *****************************************************
     **** Lottie Animation
     ****************************************************** */
    implementation(libs.lottie.compose)


    /* *****************************************************
     **** Coil Image Loading
     ****************************************************** */
    implementation(libs.coil.compose)
    implementation(libs.coil.video)

    /* *****************************************************
     **** Download File
    ****************************************************** */
    implementation(libs.work.runtime.ktx)

    /* *****************************************************
    **** Dependency-Hilt Injection
    ****************************************************** */
    implementation(libs.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.work)


    /* *****************************************************
     **** Material
     ****************************************************** */
    implementation(libs.androidx.material)

    /* *****************************************************
    **** Preference
    ****************************************************** */

    implementation(libs.datastore.preferences)

    /* *****************************************************
    **** Shared Element Transition
    ****************************************************** */

    implementation(libs.ui)

    /* *****************************************************
   **** Markdown text
   ****************************************************** */

    implementation(libs.compose.markdown)

    /* *****************************************************
      **** Ktor
      ****************************************************** */

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.client.content.negotiation.jvm)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.android)

    /* *****************************************************
     **** Serializer
     ****************************************************** */

    implementation(libs.kotlinx.serialization.json)

    /* *****************************************************
      **** Animation
      ****************************************************** */
    implementation("androidx.compose.animation:animation:1.7.8")

}