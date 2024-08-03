# Keep all public classes, methods, and fields
-keep public class * {
    public *;
}

# Keep classes with specific annotations
-keep @interface com.example.yourannotation.Keep

# Keep all classes and methods used by reflection
-keep class * {
    @com.example.yourannotation.Keep *;
}

# Keep all Activity subclasses
-keep class * extends android.app.Activity {
    <init>(...);
}

# Keep all Service subclasses
-keep class * extends android.app.Service {
    <init>(...);
}

# Keep all BroadcastReceiver subclasses
-keep class * extends android.content.BroadcastReceiver {
    <init>(...);
}

# Keep all ContentProvider subclasses
-keep class * extends android.content.ContentProvider {
    <init>(...);
}

# Keep all Fragment subclasses
-keep class * extends android.app.Fragment {
    <init>(...);
}

# Keep all custom views
-keep class * extends android.view.View {
    <init>(...);
}

# Retrofit and Gson rules
-keep class com.example.yourpackage.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }