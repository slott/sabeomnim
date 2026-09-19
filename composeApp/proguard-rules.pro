# Compose Multiplatform / Android ProGuard Rules
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

-ignorewarnings

# Media3 ExoPlayer
-dontwarn androidx.media3.**

# Kotlinx Serialization
-keepattributes *Annotation*,InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# AndroidX Core & Lifecycle
-dontwarn androidx.lifecycle.**
