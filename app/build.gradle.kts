plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
    id("kotlin-kapt")
}

android {
    namespace = "com.chemin.lumappstest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.chemin.lumappstest"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.app.compat)
    implementation(libs.design.marerial)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.androidx.paging)
    implementation(libs.dagger.main)
    implementation(libs.anvil.annotations)
    implementation(libs.bundles.androidx.room)
    implementation(libs.bundles.network.okhttp)
    implementation(libs.bundles.network.retrofit)
    kapt(libs.dagger.apt)
    kapt(libs.androidx.room.apt)

    implementation(project(":Design"))

    testImplementation(libs.test.junit)
    testImplementation(libs.test.truth.core)
    testImplementation(libs.test.coroutine)

    androidTestImplementation(libs.test.android.junit)
    androidTestImplementation(libs.test.android.espresso)
}