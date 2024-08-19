# Hilt (Dagger)
-keepclassmembers,allowobfuscation class * {
    @dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories$HiltWrapper_* *;
}
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-keep @dagger.hilt.InstallIn class * { *; }
-keep @dagger.hilt.components.SingletonComponent class * { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keep class kotlin.** { *; }
-keep class androidx.activity.ComponentActivity { *; }
-keep class androidx.lifecycle.LifecycleOwner { *; }

# ViewModel
-keep class androidx.lifecycle.ViewModel { *; }
-keepclassmembers class androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# DataStore
-keep class androidx.datastore.** { *; }
-keep class com.google.protobuf.** { *; }


# WorkManager

-keep class androidx.work.** { *; }
-keep class androidx.work.impl.** { *; }
-keep class androidx.work.impl.background.systemalarm.** { *; }
-keep class androidx.work.impl.background.systemjob.** { *; }
-keep class androidx.work.impl.background.gcm.** { *; }
-keep class androidx.work.impl.background.firebase.** { *; }
-keep class androidx.work.impl.utils.** { *; }
-keep class androidx.work.impl.constraints.** { *; }

-dontwarn androidx.work.**

# General AndroidX libraries
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Retrofit (if used)
#-keep class retrofit2.** { *; }
#-keep interface retrofit2.** { *; }
#-dontwarn retrofit2.**

## Gson (if used)
#-keep class com.google.gson.* { *; }
#-dontwarn com.google.gson.**
#
## OkHttp (if used)
#-keep class okhttp3.** { *; }
#-dontwarn okhttp3.**

# For reflection in general (use with caution)
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers class ** {
    @androidx.annotation.Keep *;
}

# Retain specific methods and fields (example)
-keepclassmembers class ** {
    public <methods>;
    public <fields>;
}

# Custom classes (example)
-keep class com.hd.** { *; }


-keepclassmembers class com.hd.misaleawianegager.utils.compose.**

-keepclassmembers class android.content.Context {
    public java.io.FileOutputStream openFileOutput(java.lang.String, int);
}

-keepclassmembers class android.content.Context {
    public java.io.FileInputStream openFileInput(java.lang.String);
}

# Prevents obfuscation of Parcelable classes (example)
-keepclassmembers class * implements android.os.Parcelable {
  static ** CREATOR;
}

# Asset handling (example)
-keep class ** {
    public void loadAsset(...);
    public void loadAssets(...);
    public void getAssets(...);
}

# Internal file access
-keep class ** {
    public void openFileInput(...);
    public void openFileOutput(...);
    public void getFilesDir(...);
    public void getCacheDir(...);
    public void getDir(...);
}



# Additional rules for other libraries or use cases can be added here

# Keep everything in release builds
#-dontobfuscate

# Optimize the code
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Ignore warnings (adjust as needed)
-dontwarn javax.annotation.**
-dontwarn org.joda.time.**
-dontwarn javax.inject.**
-dontwarn dagger.**

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile