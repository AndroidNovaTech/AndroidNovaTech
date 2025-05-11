plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias (libs.plugins.kotlin.kapt )
}

android {
    namespace = "com.zain.giftcard"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.zain.giftcard"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.common)
    implementation(libs.androidx.ui.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.lottie)
    implementation(libs.glide)

    implementation(libs.jetbrains.kotlinx.coroutines.play.services)

    // Google Ads
    implementation(libs.play.services.ads)
    // Biometric auth
    implementation(libs.androidx.biometric)
    // Drag and Drop List
    implementation(libs.draglistview)

    // Hilt for Dependency Injection


    // Unit Testing
    testImplementation(libs.junit)
    // Android Instrumentation Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    kapt(libs.compiler)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.scratchview)
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
}








