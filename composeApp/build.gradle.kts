import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.playPublisher)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.fragment)
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.coroutines.android)

            // Media3 ExoPlayer for high-performance video & audio
            implementation(libs.media3.exoplayer)
            implementation(libs.media3.ui)
            implementation(libs.media3.common)

            // Firebase Crashlytics & Analytics
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
        }

        iosMain.dependencies {
            // Native AVPlayer, AVSpeechSynthesizer available via Kotlin/Native cinterop
        }
    }
}

fun buildVersionCode(versionName: String): Int {
    val cleanVersion = versionName.lowercase().replace("-", "")
    val parts = cleanVersion.split(".")
    
    val yearPart = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val year = if (yearPart >= 2000) yearPart % 100 else yearPart 
    
    val week = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val releasePart = parts.getOrNull(2) ?: "0"
    
    var candidate = 99
    var release = 0

    if (releasePart.contains("snapshot")) {
        candidate = 0
        release = releasePart.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
    } else if (releasePart.contains("rc")) {
        val rcParts = releasePart.split("rc")
        release = rcParts.getOrNull(0)?.toIntOrNull() ?: 0
        candidate = rcParts.getOrNull(1)?.toIntOrNull() ?: 1
    } else {
        release = releasePart.toIntOrNull() ?: 0
    }

    return (year * 1000000) + (week * 10000) + (release * 100) + candidate
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

val appVersionName = project.findProperty("versionName") as? String ?: "1.0.5"
val appVersionCode = buildVersionCode(appVersionName)

println("Configuring Sabeomnim: VersionName=$appVersionName, VersionCode=$appVersionCode")

android {
    namespace = "com.sabeomnim.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "dk.slott_hansen.sabeomnim"
        minSdk = 26
        targetSdk = 36
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("myKeyAlias", "")
            keyPassword = localProperties.getProperty("myKeyPassword", "")
            storeFile = file("lego_keystore.jks")
            storePassword = localProperties.getProperty("myStorePassword", "")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

play {
    val serviceAccountFile = rootProject.file("play-service-account.json")
    if (serviceAccountFile.exists()) {
        serviceAccountCredentials.set(serviceAccountFile)
    }
    track.set("internal")
    defaultToAppBundles.set(true)
}
