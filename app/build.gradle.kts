import java.util.Properties
import java.io.FileInputStream


fun getLocalProperty(key: String, defaultValue: String): String {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    return if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
        val value = localProperties.getProperty(key)

        if (!value.isNullOrBlank()) {
            "\"$value\"" // Wrap in quotes for BuildConfig
        } else {
            "\"$defaultValue\""
        }
    } else {
        "\"$defaultValue\""
    }
}

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
        versionCode = 223
        versionName = "3.11"

        testInstrumentationRunner = "com.hd.misaleawianegager.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "BASE_URL",
            getLocalProperty("BASE_URL", "https://misale-latest-kzx7.onrender.com")
        )      

        println("DEBUG: finalBaseUrl is ${getLocalProperty("BASE_URL", "https://misale-latest-kzx7.onrender.com")})")
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
        buildConfig = true // This is essential
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
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
    implementation(libs.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.startup.runtime)
    implementation("mx.platacard:compose-pager-indicator:0.0.8")

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


    /* *****************************************************
     **** Test
     ****************************************************** */

    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    implementation(libs.ktor.client.mock.jvm)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.hilt.android.testing.v244)
    androidTestImplementation("org.assertj:assertj-core:3.27.3")
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.ktor.client.mock)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation("app.cash.turbine:turbine:1.2.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    implementation("androidx.test:runner:1.6.2")
    implementation("androidx.test:rules:1.6.1")

    /* *****************************************************
        **** Util
        ****************************************************** */

    implementation("androidx.multidex:multidex:2.0.0")
}
